package datos;

import java.util.concurrent.RecursiveAction;

@SuppressWarnings("serial")
public class MergeSortConcurrente extends RecursiveAction {
	private static final int THRESHOLD = 1000; // Umbral para cambiar a ordenamiento secuencial
	private final int[] array;
	private final int[] temp;
	private final int left;
	private final int right;

	public MergeSortConcurrente(int[] array, int[] temp, int left, int right) {
		this.array = array;
		this.temp = temp;
		this.left = left;
		this.right = right;
	}

	// Método que define la lógica de la tarea a ejecutar en paralelo
	@Override
	protected void compute() {
		if (right - left < THRESHOLD) {
			// Si el segmento es pequeño, utiliza ordenamiento secuencial
			mergeSortSequential(array, temp, left, right);
			return;
		}

		int mid = left + (right - left) / 2; // Calcula el punto medio
		
		// Crea tareas para ordenar ambas mitades en paralelo
		MergeSortConcurrente leftTask = new MergeSortConcurrente(array, temp, left, mid);
		MergeSortConcurrente rightTask = new MergeSortConcurrente(array, temp, mid + 1, right);
		
		// Ejecuta ambas tareas en paralelo
		invokeAll(leftTask, rightTask);
		
		// Fusiona las mitades ordenadas
		merge(array, temp, left, mid, right);
	}

	private static void mergeSortSequential(int[] array, int[] temp, int left, int right) {
		if (left >= right)
			return;
		int mid = left + (right - left) / 2;
		mergeSortSequential(array, temp, left, mid);
		mergeSortSequential(array, temp, mid + 1, right);
		merge(array, temp, left, mid, right);
	}

	// Fusiona dos subarreglos ordenados
	private static void merge(int[] array, int[] temp, int left, int mid, int right) {
        // Copia el segmento relevante al arreglo auxiliar
        System.arraycopy(array, left, temp, left, right - left + 1);
        
        int i = left;      // Índice para la mitad izquierda
        int j = mid + 1;   // Índice para la mitad derecha
        int k = left;      // Índice para el arreglo original
        
     // Compara y fusiona los elementos de ambas mitades
		while (i <= mid && j <= right) {
			if (temp[i] <= temp[j]) {
				array[k++] = temp[i++]; // Elemento de la izquierda es menor o igual
			} else {
				array[k++] = temp[j++]; // Elemento de la derecha es menor
			}
		}
		
		// Copia los elementos restantes de la mitad izquierda, si los hay
		while (i <= mid) {
			array[k++] = temp[i++];
		}
		
		// No es necesario copiar la mitad derecha restante
	}
}
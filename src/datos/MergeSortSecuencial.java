package datos;

public class MergeSortSecuencial {

	// Realiza el ordenamiento MergeSort. Se envia por parametro el array a ordenar
	public static void sort(int[] array) {
		if (array.length <= 1)
			return;
		int[] temp = new int[array.length]; // Arreglo auxiliar para la fusión
		mergeSort(array, temp, 0, array.length - 1); // Llamada recursiva inicial
	}

	// Método recursivo que divide el arreglo y ordena las subpartes
	private static void mergeSort(int[] array, int[] temp, int left, int right) {
		if (left >= right)
			return;
		int mid = left + (right - left) / 2; // Calcula el punto medio
		mergeSort(array, temp, left, mid); // Ordena la mitad izquierda
		mergeSort(array, temp, mid + 1, right); // Ordena la mitad derecha
		merge(array, temp, left, mid, right); // Fusiona ambas mitades ordenadas
	}

	// Fusiona dos subarreglos ordenados en uno solo
	private static void merge(int[] array, int[] temp, int left, int mid, int right) {
		// Copia el segmento relevante del arreglo original al auxiliar
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
		
		// No es necesario copiar el resto de la mitad derecha porque ya están en su lugar
	}
}

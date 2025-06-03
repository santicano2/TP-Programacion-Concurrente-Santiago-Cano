package test;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

import datos.MergeSortConcurrente;
import datos.MergeSortSecuencial;

public class Test {

    // Método que genera un arreglo aleatorio de tamaño n con valores entre min y max
    public static int[] generarArrayAleatorio(int n, int min, int max) {
        int[] array = new int[n];
        Random random = new Random();

        // Llena el arreglo con valores aleatorios en el rango especificado
        for (int i = 0; i < n; i++) {
            array[i] = random.nextInt(max - min + 1) + min;
        }

        return array;
    }

    public static void main(String[] args) {
        // Genera un arreglo aleatorio de cantidad de elementos
        int[] aux = generarArrayAleatorio(10_000, 1, 9000);

        // Crea copias del arreglo para cada método de ordenamiento
        int[] A = Arrays.copyOf(aux, aux.length); // Para MergeSort secuencial
        int[] B = Arrays.copyOf(aux, aux.length); // Para MergeSort concurrente

        System.out.println("El arreglo tiene " + aux.length + " elementos");

        // Medición del tiempo de ejecución para MergeSort secuencial
        long inicioSecuencial = System.currentTimeMillis();
        MergeSortSecuencial.sort(A);
        long finSecuencial = System.currentTimeMillis();
        System.out.println("Tiempo MergeSort Secuencial: " + (finSecuencial - inicioSecuencial) + " ms");

        // Medición del tiempo de ejecución para MergeSort concurrente
        try (
		ForkJoinPool pool = new ForkJoinPool()) {
			int[] temp = new int[B.length]; // Arreglo auxiliar para la fusión
			MergeSortConcurrente task = new MergeSortConcurrente(B, temp, 0, B.length - 1);
			long inicioConcurrente = System.currentTimeMillis();
			pool.invoke(task); // Ejecuta la tarea en el pool de hilos
			long finConcurrente = System.currentTimeMillis();
			System.out.println("Tiempo MergeSort Concurrente: " + (finConcurrente - inicioConcurrente) + " ms");
		}
    }
}


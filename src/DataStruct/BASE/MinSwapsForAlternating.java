package DataStruct.BASE;
import java.util.ArrayList;
import java.util.List;

public class MinSwapsForAlternating {

    public static class Swap {
        int fromIndex, toIndex;

        public Swap(int fromIndex, int toIndex) {
            this.fromIndex = fromIndex;
            this.toIndex = toIndex;
        }

        @Override
        public String toString() {
            return "Swap elements at indices " + fromIndex + " and " + toIndex;
        }
    }

    public static List<Swap> minSwapsToAlternate(int[] arr) {
        List<Integer> mismatchedOddIdx = new ArrayList<>();
        List<Integer> mismatchedEvenIdx = new ArrayList<>();

        // Determine the expected starting type based on count
        int oddCount = 0, evenCount = 0;
        for (int num : arr) {
            if (num % 2 == 0) evenCount++;
            else oddCount++;
        }
        boolean evenStart = evenCount >= oddCount;

        // Find all mismatched positions
        for (int i = 0; i < arr.length; i++) {
            if ((i % 2 == 0) == evenStart) {
                // We expect an even number here
                if (arr[i] % 2 != 0) mismatchedOddIdx.add(i);
            } else {
                // We expect an odd number here
                if (arr[i] % 2 == 0) mismatchedEvenIdx.add(i);
            }
        }

        // Create swap actions
        List<Swap> swaps = new ArrayList<>();
        int minSwaps = Math.min(mismatchedOddIdx.size(), mismatchedEvenIdx.size());
        for (int i = 0; i < minSwaps; i++) {
            swaps.add(new Swap(mismatchedOddIdx.get(i), mismatchedEvenIdx.get(i)));
            // Execute the swap to simulate the result
            int temp = arr[mismatchedOddIdx.get(i)];
            arr[mismatchedOddIdx.get(i)] = arr[mismatchedEvenIdx.get(i)];
            arr[mismatchedEvenIdx.get(i)] = temp;
        }

        System.out.println("Minimum swaps needed: " + minSwaps);
        return swaps;
    }

    public static void main(String[] args) {
        // int[] arr = {2, 3, 5, 1, 4, 8};
        int[] arr = {3, 1, 2};
        
        List<Swap> swapOperations = minSwapsToAlternate(arr);
        if (swapOperations != null) {
            swapOperations.forEach(System.out::println);
        }
    }
}

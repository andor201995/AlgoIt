package interview

/**
 * Checks if two arrays are subsets of each other based on their unique elements.
 * This means every unique element in `arr1` must be present in `arr2`, and vice-versa.
 * The frequency of elements is not considered.
 *
 */
private fun main() {
    val result = subsetArray(
        intArrayOf(1, 2, 3, 4, 4, 2, 1, 2, 3, 4),
        intArrayOf(1, 2, 3, 4, 1, 2, 3, 1, 1, 4)
    )

    println("Expected: true \nResult: $result")
}

/**
 * Time Complexity: O(N + M), where N is the number of elements in `arr1` and M is the number of elements in `arr2`.
 *                  This is due to the conversion of arrays to sets.
 * Space Complexity: O(N + M), for storing the unique elements in two sets.
 *
 * @param arr1 The first integer array.
 * @param arr2 The second integer array.
 * @return `true` if both arrays contain the same unique elements, `false` otherwise.
 */
private fun subsetArray(arr1: IntArray, arr2: IntArray): Boolean {
    val arrSet1 = arr1.toSet()
    val arrSet2 = arr2.toSet()
    if (arrSet1.size != arrSet2.size) return false

    for (i in arrSet1) {
        if (!arrSet2.contains(i)) return false
    }

    return true
}
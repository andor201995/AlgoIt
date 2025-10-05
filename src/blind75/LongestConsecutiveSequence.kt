package blind75

/**
 * 128. Longest Consecutive Sequence
 * https://leetcode.com/problems/longest-consecutive-sequence?envType=problem-list-v2&envId=oizxjoit
 *
 * Given an unsorted array of integers nums, return the length of the longest consecutive elements sequence.
 *
 * You must write an algorithm that runs in O(n) time.
 */

private fun main() {
    val result = longestConsecutive(intArrayOf(100,4,200,3,1,3,2,1))
    // longest consecutive 1,1,2,3,3,4,100,200 -> 1,2,3,4
    println("Expected: 4 \nResult: $result")
}

/**
 * Time Complexity: O(n)
 *
 * The time complexity is O(n) because we iterate through the input array `nums` a
 * constant number of times.
 * 1. Creating the `numSet` from `nums` takes O(n) time.
 * 2. The main `for` loop iterates through each number in `nums`.
 * 3. For each number, we potentially have two `while` loops to find the bounds of the consecutive sequence.
 *    However, each number from the `numSet` is visited at most once by the `left` pointer and at most once
 *    by the `right` pointer throughout the entire execution of the function. This is because we remove the numbers
 *    from the set as we find them.
 * 4. Therefore, the total number of operations in the `while` loops across all iterations of the `for` loop is
 *    proportional to `n`.
 *
 * This makes the overall time complexity O(n) + O(n) = O(n).
 *
 * Space Complexity: O(n)
 * We are using a HashSet to store all the numbers from the input array, which will take up O(n) space in the worst case.
 */
private fun longestConsecutive(nums: IntArray): Int {
    // Create a HashSet from the input array for O(1) average time complexity for lookups and removals.
    // This also handles duplicate numbers automatically.
    val numSet = nums.toHashSet()
    var max = 0

    // Iterate through each number in the original array.
    for (num in nums) {
        // If the number has already been visited and removed as part of a previous sequence, skip it.
        if (!numSet.contains(num)) {
            continue
        }

        // `num` is a potential start of a new sequence.
        // We will expand outwards from `num` to find the full length of the sequence.
        var left = num
        var right = num

        // Expand to the left to find the lower bound of the consecutive sequence.
        // Remove elements as we find them to avoid re-processing.
        while (numSet.contains(--left)) {
            numSet.remove(left)
        }

        // Expand to the right to find the upper bound of the consecutive sequence.
        // Remove elements as we find them to avoid re-processing.
        while (numSet.contains(++right)) {
            numSet.remove(right)
        }

        // The length of the sequence is the difference between the exclusive upper and lower bounds.
        // For example, if the sequence is {1, 2, 3}, `right` will be 4 and `left` will be 0 after the loops.
        // The length is 4 - 0 - 1 = 3.
        val count = right - left - 1
        if (count > max) {
            max = count
        }
    }
    return max
}
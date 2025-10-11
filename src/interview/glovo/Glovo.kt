package interview.glovo

/**
 * [  move all k to last keeping array in place and in order ]
 * { Similar https://leetcode.com/problems/move-zeroes }
 *
 * Given an integer array nums, move all 0's to the end of it while maintaining the relative order of the non-zero elements.
 * Note that you must do this in-place without making a copy of the array.
 */
private fun main() {
    val input = intArrayOf(1, 2, 1, 3, 4, 5, 1, 2, 1)
    moveElementToLast(input, 1)

    println("Expected: [2,3,4,5,2,1,1,1,1] \nResult: ${input.contentToString()}")
}

/**
 * Time Complexity: O(n)
 * We iterate through the array using two pointers, `prev` and `next`. In the worst-case scenario,
 * each pointer will traverse the entire array once, making the time complexity linear.
 *
 * Space Complexity: O(1)
 * The algorithm uses a constant amount of extra space for the two pointers and a temporary
 * variable for swapping. The array is modified in-place, so no additional space
 * proportional to the input size is required.
 *
 * This function moves all occurrences of a specific `element` to the end of the `nums` array
 * while maintaining the relative order of the other elements. It uses a two-pointer approach.
 *
 * @param nums The input array of integers.
 * @param element The integer to move to the end of the array.
 */
private fun moveElementToLast(nums: IntArray, element: Int) {
    var prev = 0
    var next = 1

    while (next < nums.size) {
        // If the element at the `prev` pointer is not the target element,
        // it's already in its correct relative position.
        // We move both pointers forward to continue scanning.
        if(nums[prev] != element) {
            prev++
            next = prev + 1
            continue
        }

        // If the element at `prev` is the target, we look for an element at `next`
        // to swap with.
        // If `nums[next]` is not the target, we swap the elements at `prev` and `next`.
        if(nums[prev] != nums[next]) {
            val temp = nums[prev]
            nums[prev] = nums[next]
            nums[next] = temp

            // After a successful swap, we move `prev` to the next position
            // to check the new element that was swapped in.
            prev++
            next++
        } else {
            // If both `nums[prev]` and `nums[next]` are the target element,
            // we only increment `next` to find the next non-target element to swap.
            next++
        }
    }
}
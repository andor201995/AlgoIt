package blind75

/**
 * [ 1. Two Sum ]
 * { https://leetcode.com/problems/two-sum?envType=problem-list-v2&envId=oizxjoit }
 *
 * Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.
 * You may assume that each input would have exactly one solution, and you may not use the same element twice.
 * You can return the answer in any order.
 */
private fun main() {
    val result = twoSum(intArrayOf(2,7,10,3,5), 9)

    println("Expected: [1,0] \nResult: ${result.contentToString()}")
}

/**
 * Time Complexity: O(n)
 * We iterate through the list of n numbers only once. The hash map operations
 * (insertion and lookup) take constant time on average, O(1).
 *
 * Space Complexity: O(n)
 * In the worst-case scenario, we might store all n numbers in the hash map
 * before finding the pair that adds up to the target.
 *
 * @param nums The input array of integers.
 * @param target The target integer sum.
 * @return An array containing the indices of the two numbers that sum up to the target.
 */
private fun twoSum(nums: IntArray, target: Int): IntArray {
    // A hash map to store the numbers we've seen and their corresponding indices.
    // Key: number, Value: index
    val numMap = hashMapOf<Int,Int>()
    var result: IntArray = intArrayOf()

    // Iterate through the array once.
    for(i in 0 until nums.size) {
        // Calculate the complement, which is the number we need to find
        // to reach the target with the current number.
        val complement = target - nums[i]

        // Check if the complement exists in our map.
        // If it does, we have found our pair.
        if(numMap.contains(complement)) {
            // The indices are the current index `i` and the index of the complement stored in the map.
            result = intArrayOf(i, requireNotNull(numMap[complement]))
            // Since we found the solution, we can break out of the loop.
            break
        } else {
            // If the complement is not in the map, add the current number and its index to the map.
            // We do this so that subsequent numbers can check if the current number is their complement.
            numMap[nums[i]] = i
        }
    }

    return result
}
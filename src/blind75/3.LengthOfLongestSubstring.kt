package blind75

/**
 * [ 3. Longest Substring Without Repeating Characters ]
 * { https://leetcode.com/problems/longest-substring-without-repeating-characters?envType=problem-list-v2&envId=oizxjoit }
 *
 * Given a string s, find the length of the longest substring without duplicate characters.
 */
private fun main() {
    val result = lengthOfLongestSubstring("abcabcbb")

    println("Expected: 3 \nResult: $result")
}

/**
 * Finds the length of the longest substring without repeating characters using the sliding window technique.
 *
 * Time Complexity: O(n)
 * The algorithm iterates through the string with two pointers, `left` and `right`.
 * Each character is visited at most twice (once by the `right` pointer and once by the `left` pointer).
 * This makes the time complexity linear with respect to the length of the string, n.
 *
 * Space Complexity: O(k)
 * The space complexity is determined by the size of the `strSet`, which stores the characters in the current window.
 * In the worst case, the set will store k unique characters, where k is the size of the character set (e.g., ASCII).
 * It's O(min(n, m)) where n is the length of the string and m is the size of the character set.
 *
 * @param str The input string.
 * @return The length of the longest substring without repeating characters.
 */
private fun lengthOfLongestSubstring(str: String): Int {
    // Pointers for the sliding window
    var left = 0
    var right = 0

    // A HashSet to keep track of characters currently in the sliding window.
    // This allows for O(1) average time complexity for adding, removing, and checking for existence.
    val strSet = hashSetOf<Char>()
    var result = 0

    // The `right` pointer expands the window to the right.
    while (right < str.length) {
        // If the character at the `right` pointer is already in our set,
        // it means we have a duplicate in the current window.
        if (strSet.contains(str[right])) {
            // To resolve the duplicate, we must shrink the window from the left.
            // Remove the character at the `left` pointer from the set and move `left` pointer to the right.
            // We keep doing this until the duplicate character is removed from the window.
            strSet.remove(str[left])
            left++
        } else {
            // If the character is not in the set, it's unique within the current window.
            // Add it to the set and expand the window by moving the `right` pointer.
            strSet.add(str[right])
            right++
        }

        // After every potential expansion or contraction of the window,
        // update the result with the maximum size the window has reached so far.
        // The size of the set represents the length of the current non-repeating substring.
        result = maxOf(result, strSet.size)
    }
    return result
}
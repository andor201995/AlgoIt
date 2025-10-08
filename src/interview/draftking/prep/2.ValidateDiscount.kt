package interview.draftking.prep

/**
 * [ Validate Customer Discounts ]
 * { Chat GTP }
 *
 * You are given two arrays:
 *
 * customers[ i ] — the total spending of the i-th customer.
 *
 * discounts[ i ] — a list of discounts applied to that customer.
 *
 * Each customer can receive at most three discounts.
 * Higher-spending customers should not receive a smaller total discount than any lower-spending customer.
 * Higher-spending customers should not receive a smaller max discount than any lower-spending customer.
 *
 * Return true if the dataset is valid according to these rules, otherwise return false.
 */
private fun main() {
    val result = validateDiscountRules(
        intArrayOf(100, 80, 50),
        arrayOf(
            intArrayOf(10, 15, 5),
            intArrayOf(10, 5),
            intArrayOf(5),
        )
    )

    println("Expected: true \nResult: $result")
}

/**
 * Validates if the customer and discount data adheres to the given rules.
 *
 * Time Complexity: O(n log n)
 * The complexity is dominated by the sorting of the customers list, where n is the number of customers.
 * - Mapping the input arrays to a list of Customer objects takes O(n).
 * - Sorting the list takes O(n log n).
 * - The final validation loop takes O(n).
 *
 * Space Complexity: O(n)
 * We create a new list of Customer objects to hold the processed data, which requires O(n) space.
 *
 * @param customerSpending An array of customer spending amounts.
 * @param customerDiscounts A 2D array of discounts for each customer.
 * @return `true` if the data is valid, `false` otherwise.
 */
private fun validateDiscountRules(
    customerSpending: IntArray,
    customerDiscounts: Array<IntArray>
): Boolean {
    // 1. Create a structured list of Customer objects.
    // This makes the code cleaner and calculates the total discount and count once.
    val customers = customerSpending.zip(customerDiscounts)

    // 2. Sort customers by their spending in descending order.
    // This is crucial for checking the rule that higher spenders get larger or equal discounts.
    val sortedCustomers = customers.sortedByDescending { it.first }

    // 3. Iterate through the sorted list to validate the two rules.
    for (i in 0 until sortedCustomers.size) {
        val currentCustomer = sortedCustomers[i]

        // Rule 1: Check if any customer has more than three discounts.
        if (currentCustomer.second.size > 3) {
            return false
        }

        if (i < sortedCustomers.size - 1) {
            val nextCustomer = sortedCustomers[i + 1]

            // Rule 2: Check if a higher-spending customer has a smaller total discount
            if (currentCustomer.second.sum() < nextCustomer.second.sum()) {
                return false
            }

            // Rule 3: Check is higher spending customers has smaller max discount
            if(currentCustomer.second.max() < nextCustomer.second.max()) {
                return false
            }
        }
    }

    // If all checks pass after iterating through all customers, the dataset is valid.
    return true
}
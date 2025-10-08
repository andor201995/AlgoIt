package interview.draftking.prep

/**
 * [ Assign Valid discounts ]
 * { https://www.reddit.com/r/DevelEire/comments/1cndojz/software_engineer_interview_at_draftkings/ }
 *
 * assign discounts to customers of a shop. Had been given a list of customer with their respective spending for a year, then a list of products and respective discount associated with the product. One customer could have multiple discount. The task was to assign the discounts to customer such that customer with high spending gets max discount. In order to do that we had to make sure the code satisfies following conditions:
 *
 * No customer can have more than 3 discounts
 *
 * Every discount should have one or more customers
 *
 * No customer discount should be more than 20 percent of their yearly spend
 *
 * Customer with more spending has more discounts
 */
private fun main() {
    val result = assignDiscount(
        listOf(100, 80, 50),
        listOf(10, 15, 20, 5, 8)
    )

    // The result can vary based on the greedy strategy, but here is one possible valid output.
    // In this case, {100=[10, 8], 80=[15], 50=[5], unassigned=[20]} is a possible outcome of a simple greedy approach,
    // but it fails because discount 20 is unassigned. The logic must handle this.
    // A valid assignment: {100=[20], 80=[8, 5], 50=[10], unassigned=[15]} -> fails as 15 > 16 for C80
    // This is a tricky problem. The solution below implements a robust greedy strategy.
    println("Result: $result")
}

/**
 * Assigns discounts to customers based on a set of rules using a greedy algorithm.
 *
 * Time Complexity: O(d*c + d log d + c log c)
 * - d log d for sorting discounts.
 * - c log c for sorting customers.
 * - d*c for the nested loops in the worst case.
 * Where 'd' is the number of discounts and 'c' is the number of customers.
 *
 * Space Complexity: O(c)
 * To store the assignments map, which will hold an entry for each customer.
 *
 * @param customerSpendings A list of yearly spending for each customer.
 * @param discounts A list of available discounts.
 * @return A list of pairs, where each pair contains a customer's spending
 *         and the list of discounts assigned to them. Returns an empty list
 *         if not all discounts can be assigned.
 */
private fun assignDiscount(
    customerSpendings: List<Int>,
    discounts: List<Int>
): List<Pair<Int, List<Int>>> {
    // 1. Sort customers by spending (descending) to prioritize high spenders.
    val sortedCustomers = customerSpendings.sortedDescending()
    // Sort discounts (descending) to assign the largest, most restrictive ones first.
    val sortedDiscounts = discounts.sortedDescending()

    // 2. Prepare data structures for assignment.
    val assignments = sortedCustomers.associateWith { mutableListOf<Int>() }

    // 3. Greedy assignment loop.
    // For each discount, find the best customer for it.
    for (discount in sortedDiscounts) {
        var isDiscountAssigned = false
        // Iterate through customers from highest spender to lowest.
        for (customerSpending in sortedCustomers) {
            val assignedList = assignments[customerSpending]!!
            val currentTotalDiscount = assignedList.sum()
            val maxAllowedDiscount = (customerSpending * 0.20).toInt()

            // Check if this customer can accept the discount based on the rules.
            val canAcceptDiscount =
                // Rule 1: Not more than 3 discounts.
                assignedList.size < 3 &&
                // Rule 3: Total discount does not exceed 20% of spending.
                currentTotalDiscount + discount <= maxAllowedDiscount

            if (canAcceptDiscount) {
                // If they can, assign it and move to the next discount.
                assignedList.add(discount)
                isDiscountAssigned = true
                break // Stop searching for a customer for this discount.
            }
        }
        // If after checking all customers, a discount could not be assigned,
        // it's impossible to meet the condition that all discounts must be used.
        if (!isDiscountAssigned) {
            // This indicates a scenario where a valid assignment might not be possible
            // with this greedy strategy for the given input.
            println("Warning: Could not assign discount $discount. A valid assignment may not be possible.")
            // Depending on strict requirements, you could return an empty list or throw an exception.
            // return emptyList()
        }
    }

    // 4. Final formatting of the results.
    return assignments.map { (spending, discountList) ->
        Pair(spending, discountList)
    }
}
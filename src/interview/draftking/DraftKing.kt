package interview.draftking

data class Discount(
    val id: Int,
    val productId: Int,
    val value: Float,
)

data class AssignedDiscount(
    val discountId: Int,
    val customerId: Int,
)

data class Customer(
    val id: Int,
    val yearlySpent: Float,
)

data class MappedCustomer(
    val customer: Customer,
    val discountList: MutableList<Discount>,
)

/**
 * ## Problem: Validate Discount Assignments
 *
 * This function validates a list of discount assignments based on a set of rules.
 *
 * ### Rules:
 * 1.  A customer cannot have more than 3 discounts.
 * 2.  The total value of discounts for a customer cannot exceed 20% of their yearly spending.
 * 3.  All available discounts must be assigned to customers.
 * 4.  Customers with higher yearly spending must receive discounts with a total value greater than or equal to the total value of discounts received by customers with lower yearly spending.
 *
 * ### Time Complexity:
 * -   **Original:** O(A * (C + D)), where:
 *     -   `A` is the number of assigned discounts.
 *     -   `C` is the number of customers.
 *     -   `D` is the number of discounts.
 * -   **Optimized:** O(A + C + D + M log M), where:
 *     -   `M` is the number of customers with assigned discounts.
 *
 * ### Space Complexity:
 * -   O(C + D + A), to store maps for customers, discounts, and the mapped customer list.
 *
 * ### Performance Improvements:
 * The original implementation used `find` inside a loop, which is inefficient.
 * By converting the `customers` and `discounts` lists to maps, we can achieve O(1) lookups.
 * Additionally, using a `MutableSet` for `leftDiscount` provides O(1) average time complexity for removals.
 *
 * @param customers The list of all customers.
 * @param discounts The list of all available discounts.
 * @param assignedDiscounts The list of assignments of discounts to customers.
 * @return `true` if all rules are satisfied, `false` otherwise.
 */
fun validateDiscount(
    customers: List<Customer>,
    discounts: List<Discount>,
    assignedDiscounts: List<AssignedDiscount>
): Boolean {
    // Optimization: Convert lists to maps for O(1) lookups.
    val customerMap = customers.associateBy { it.id }
    val discountMap = discounts.associateBy { it.id }

    val mappedCustomerList = mutableMapOf<Int, MappedCustomer>()
    val leftDiscount = discounts.map { it.id }.toMutableSet()

    assignedDiscounts.forEach { assignedDiscount ->
        val currentCustomer = customerMap[assignedDiscount.customerId]!!
        val currentDiscount = discountMap[assignedDiscount.discountId]!!

        mappedCustomerList.getOrPut(currentCustomer.id) {
            MappedCustomer(customer = currentCustomer, discountList = mutableListOf())
        }.discountList.add(currentDiscount)

        leftDiscount.remove(currentDiscount.id)
    }

    // Rule 3: All discounts are used.
    if (leftDiscount.isNotEmpty()) {
        return false
    }

    val sortedMappedCustomerBySpent =
        mappedCustomerList.values.sortedByDescending { it.customer.yearlySpent }

    for (i in sortedMappedCustomerBySpent.indices) {
        val currentMappedCustomer = sortedMappedCustomerBySpent[i]

        // Rule 1: Not more than 3 discounts.
        if (currentMappedCustomer.discountList.size > 3) {
            return false
        }

        val totalDiscount = currentMappedCustomer.discountList.sumOf { it.value.toDouble() }
        val maxDiscountAvailable = currentMappedCustomer.customer.yearlySpent * 0.2

        // Rule 2: Total discount not more than 20% of yearly spent.
        if (totalDiscount > maxDiscountAvailable) {
            return false
        }

        // Rule 4: Highest spending customer gets the highest discount.
        if (i < sortedMappedCustomerBySpent.size - 1) {
            val nextMappedCustomer = sortedMappedCustomerBySpent[i + 1]
            val totalDiscountNext = nextMappedCustomer.discountList.sumOf { it.value.toDouble() }
            if (totalDiscountNext > totalDiscount) {
                return false
            }
        }
    }

    return true
}

private fun main() {
    val customers = listOf(
        Customer(1, 100f),
        Customer(2, 80f),
        Customer(3, 50f)
    )
    val discounts = listOf(
        Discount(1, 101, 10f),
        Discount(2, 102, 15f),
        Discount(3, 103, 5f),
        Discount(4, 104, 5f),
        Discount(5, 105, 5f)
    )
    // This assignment ensures all rules are met for a 'true' result.
    val assignedDiscounts = listOf(
        AssignedDiscount(2, 1), // Customer 1 (100) gets 15f
        AssignedDiscount(3, 1), // Customer 1 gets 5f -> Total 20f (<= 20% of 100)
        AssignedDiscount(1, 2), // Customer 2 (80) gets 10f
        AssignedDiscount(4, 2), // Customer 2 gets 5f -> Total 15f (<= 20% of 80)
        AssignedDiscount(5, 3)  // Customer 3 (50) gets 5f -> Total 5f (<= 20% of 50)
    )

    val result = validateDiscount(customers, discounts, assignedDiscounts)
    println("Expected: true \nResult: $result")
}
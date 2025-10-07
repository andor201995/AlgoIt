package interview.draftking.prep

/**
 * [ Maximum Unit on a truck ]
 * { https://leetcode.com/problems/maximum-units-on-a-truck }
 *
 * You are assigned to put some amount of boxes onto one truck. You are given a 2D array boxTypes, where boxTypes[ i ] = [numberOfBoxesi, numberOfUnitsPerBoxi]:
 *
 * numberOfBoxesi is the number of boxes of type i.
 * numberOfUnitsPerBoxi is the number of units in each box of the type i.
 * You are also given an integer truckSize, which is the maximum number of boxes that can be put on the truck. You can choose any boxes to put on the truck as long as the number of boxes does not exceed truckSize.
 *
 * Return the maximum total number of units that can be put on the truck.
 */
private fun main() {
    val result = maximumUnits(
        arrayOf(
            intArrayOf(5, 10),
            intArrayOf(2, 5),
            intArrayOf(4, 7),
            intArrayOf(3, 9),
        ),
        10
    )

    println("Expected: 91 \nResult: $result")
}

/**
 * Calculates the maximum total number of units that can be put on a truck.
 * This is a greedy approach. To maximize the units, we should always prioritize
 * loading the boxes with the highest number of units per box first.
 *
 * Time Complexity: O(n log n)
 * The dominant operation is sorting the `boxTypes` array, which takes O(n log n) time,
 * where n is the number of types of boxes. The subsequent loop takes O(n) time.
 * Total time complexity = O(n log n) + O(n) = O(n log n).
 *
 * Space Complexity: O(n) or O(log n)
 * The space complexity depends on the implementation of the sorting algorithm used by
 * `sortedByDescending`. If it creates a new list, it's O(n). If it sorts in-place,
 * the space could be O(log n) for the recursion stack.
 *
 * @param boxTypes A 2D array where each element is `[numberOfBoxes, numberOfUnitsPerBox]`.
 * @param truckSize The maximum number of boxes the truck can hold.
 * @return The maximum total number of units.
 */
private fun maximumUnits(boxTypes: Array<IntArray>, truckSize: Int): Int {
    // Sort the box types in descending order based on the number of units per box.
    // This ensures we always consider the most valuable boxes first.
    val sortedBoxByUnit = boxTypes.sortedByDescending { boxPair -> boxPair[1] }

    var currentBoxFilled = 0
    var currentUnitFilled = 0

    // Iterate through the sorted list of box types.
    for (boxPair in sortedBoxByUnit) {
        val numberOfBoxes = boxPair[0]
        val unitsPerBox = boxPair[1]
        val leftTruckSize = truckSize - currentBoxFilled

        // If we can take all boxes of the current type without exceeding the truck size.
        if (leftTruckSize >= numberOfBoxes) {
            currentUnitFilled += numberOfBoxes * unitsPerBox
            currentBoxFilled += numberOfBoxes
        } else {
            // If we can only take a partial amount of the current box type.
            // Fill the remaining truck space with boxes of this type.
            currentUnitFilled += leftTruckSize * unitsPerBox
            // The truck is now full.
            currentBoxFilled = truckSize
            // Break the loop as we can't add any more boxes.
            break
        }
    }

    return currentUnitFilled
}
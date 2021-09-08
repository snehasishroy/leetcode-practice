package alternate;

/**
 * <pre>
 * https://www.geeksforgeeks.org/find-smallest-value-represented-sum-subset-given-array/
 *
 * https://www.youtube.com/watch?v=1h2eFrNFSSw
 *
 * https://stackoverflow.com/a/21078133/1206152
 *
 * Find the smallest impossible subset sum that is not possible to generate from all the given permutations of a set
 * Saw this question on Lalit Kundu youtube channel. Definitely an interesting problem to solve in linear time.
 * </pre>
 */
public class SmallestImpossibleSubsetSum {
    /*
    Time Complexity: O(nlogn) or O(n) if you use radix sort or counting sort for sorting
    Sort(A)
    candidate = 1
            for i from 1 to length(A):
            if A[i] > candidate: return candidate
   else: candidate = candidate + A[i]
            return candidate

    Related Problem: {@link PatchingArray} {@link MaximumNumberOfConsecutiveValuesYouCanMake}
    */
}

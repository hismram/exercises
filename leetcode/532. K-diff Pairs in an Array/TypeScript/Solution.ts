/*
Given an array of integers nums and an integer k, return the number of unique k-diff pairs in the array.

A k-diff pair is an integer pair (nums[i], nums[j]), where the following are true:

0 <= i, j < nums.length
i != j
nums[i] - nums[j] == k
Notice that |val| denotes the absolute value of val.

Constraints:

1 <= nums.length <= 10**4
-10**7 <= nums[i] <= 10**7
0 <= k <= 10**7
*/

// Неэффективно =(

function findPairs(nums: number[], k: number): number {
    return new PairsFinder({nums, k}).find();
};

class PairsFinder {
    private readonly _nums: number[];
    private readonly _k: number;
    private _counter: number = 0;
    private _map: {[key: number]: number[]} = {};

    constructor(opts: {nums: number[], k: number}) {
        this._nums = opts.nums.sort((a,b) => a - b);
        this._k = opts.k;
    }

    public find(): number {
        for (let i = 0; i < this._nums.length; i++) {
            for (let j = i + 1; j < this._nums.length; j++) {
                if (this._nums[j] > (this._nums[i] + this._k)) {
                    break;
                }

                if (!this._isExist(this._nums[j]) && this._nums[j] === (this._nums[i] + this._k)) {
                    this._writePair(this._nums[i], this._nums[j]);
                }
            }
        }

        return this._counter;
    }

    private _isExist(num: number): boolean {
        return !!this._map[num];
    }

    private _writePair(a: number, b: number): void {
        if (!this._map[a]) {
            this._map[a] = [b];
        } else {
            this._map[a].push(b);
        }

        if (!this._map[b]) {
            this._map[b] = [a];
        } else {
            this._map[b].push(a);
        }

        this._counter++;
    }
}
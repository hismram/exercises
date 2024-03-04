/*
Given a signed 32-bit integer x, return x with its digits reversed. If reversing x causes the value to go outside the signed 32-bit integer range [-2**31, 2**31 - 1], then return 0.
*/

function isInt32(num: number): boolean {
    return ((+num)|0) === num;
}

function reverse(x: number): number {
    let s: string = x + '';
    let result: number;
    let mult: number = 1;

    if (s[0] === '-') {
        mult = mult * -1;
        s = s.slice(1);
    }
    
    s = s.split('').reverse().join('');
    result = parseInt(s) * mult;
    
    return isInt32(result) ? result : 0;
}
/*
The string "PAYPALISHIRING" is written in a zigzag pattern on a given number of rows like this: (you may want to display this pattern in a fixed font for better legibility)

P   A   H   N
A P L S I I G
Y   I   R
And then read line by line: "PAHNAPLSIIGYIR"

Write the code that will take a string and make this conversion given a number of rows:
string convert(string s, int numRows);

Constraints:
1 <= s.length <= 1000
s consists of English letters (lower-case and upper-case), ',' and '.'.
1 <= numRows <= 1000
*/

function convert(s: string, numRows: number): string {
    const step: number = numRows + (numRows - 2 >= 0 ? numRows - 2 : 0);
    let isDiv: boolean = false;
    let result: string = '';
    // позиция курсора
    let i = 0;
    // текущая линия
    let line = 0;

    while (result.length < s.length) {
        let divider = line > 0 && line < numRows - 1;

        result += s[i];

        if (divider) {
            // Проход по центру
            // Если предыдущий был делителем
            if (isDiv) {
                // переходим к следующему
                i += line * 2;
            } else {
                // Промежуточный знак
                i += step - (line * 2);
            }
            isDiv = !isDiv;
        } else {
            i += step;
        }

        // Вышли за пределы, переходим на следующую строку
        if (i >= s.length) {
            line++;
            i = line;
            isDiv = false;
        }
    }

    return result;
}
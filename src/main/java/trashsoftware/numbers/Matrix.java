package trashsoftware.numbers;

import java.util.Arrays;

public class Matrix {

    public static final Matrix ZERO_2X2 = integerMatrix(new long[][]{
            {0, 0},
            {0, 0}
    });

    public static final Matrix ZERO_3X3 = integerMatrix(new long[][]{
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0}
    });

    public static final Matrix IDENTITY_2X2 = integerMatrix(new long[][]{
            {1, 0},
            {0, 1}
    });

    public static final Matrix IDENTITY_3X3 = integerMatrix(new long[][]{
            {1, 0, 0},
            {0, 1, 0},
            {0, 0, 1}
    });

    private final Real[][] rows;

    private Matrix(Real[][] rows) {
        this.rows = rows;
    }

    public static Matrix createInstance(Real[][] rows) {
        return new Matrix(rows);
    }

    public static Matrix integerMatrix(long[][] rows) {
        Real[][] matrix = new Real[rows.length][];
        for (int i = 0; i < rows.length; i++) {
            long[] iRow = rows[i];
            matrix[i] = new Real[iRow.length];
            for (int j = 0; j < iRow.length; j++) {
                Rational r = Rational.valueOf(iRow[j]);
                matrix[i][j] = r;
            }
        }
        return new Matrix(matrix);
    }

    public static Matrix zeroMatrix(int rowsCount, int colsCount) {
        long[][] matrix = new long[rowsCount][colsCount];
        return integerMatrix(matrix);
    }

    public static Matrix identityMatrix(int dimension) {
        long[][] matrix = new long[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            matrix[i][i] = 1;
        }
        return integerMatrix(matrix);
    }

    public Real get(int row, int col) {
        return rows[row][col];
    }

    public void set(int row, int col, Real val) {
        rows[row][col] = val;
    }

    public void toRowEchelonForm() {
        int min = Math.min(rowsCount(), columnsCount());
        int theoPivotPos = 0;
        for (int i = 0; i < min; i++) {  // pivot row
            int pivotPos = rowPivotPosition(i);
            if (theoPivotPos == columnsCount()) break;  // all remains are zero
            if (pivotPos != theoPivotPos) {
                int pivotRow = findRowWithPivot(i + 1, theoPivotPos);
                if (pivotRow == -1) {
                    theoPivotPos++;
                } else {
                    swapRows(i, pivotRow);
                }
                i--;
                continue;
            }
            Real pivot = get(i, pivotPos);
            for (int j = i + 1; j < rowsCount(); j++) {  // rows under current pivot row
                Real rowFirst = get(j, pivotPos);
                Real ratio = rowFirst.divide(pivot).negate();
                for (int c = i; c < columnsCount(); c++) {
                    Real pivotRowMultiple = get(i, c).multiply(ratio);
                    Real result = pivotRowMultiple.add(get(j, c));
                    set(j, c, result);
                }
            }
        }
    }

    public void toReducedRowEchelonForm() {
        toRowEchelonForm();
        for (int i = rowsCount() - 1; i >= 0; i--) {
            int pivotPos = rowPivotPosition(i);
            if (pivotPos == -1) continue;
            Real selfRatio = Rational.ONE.divide(get(i, pivotPos));
            for (int j = pivotPos; j < columnsCount(); j++) {
                set(i, j, get(i, j).multiply(selfRatio));  // set the line pivot to 1
            }
            Real pivot = get(i, pivotPos);
            for (int r = i - 1; r >= 0; r--) {  // reduce all rows above the current row
                Real ratio = get(r, pivotPos).divide(pivot).negate();
                for (int c = 0; c < columnsCount(); c++) {
                    set(r, c, get(i, c).multiply(ratio).add(get(r, c)));
                }
            }
        }
    }

    public Matrix augment(Matrix matrix) {
        Real[][] newMatrix = new Real[rowsCount()][];
        for (int i = 0; i < rowsCount(); i++) {
            Real[] dest = new Real[columnsCount() + matrix.columnsCount()];
            System.arraycopy(rows[i], 0, dest, 0, columnsCount());
            System.arraycopy(matrix.rows[i], 0, dest, columnsCount(), matrix.columnsCount());
            newMatrix[i] = dest;
        }
        return Matrix.createInstance(newMatrix);
    }

    public Matrix augment(Real[] vector) {
        Real[][] matrix = new Real[rowsCount()][];
        for (int i = 0; i < rowsCount(); i++) {
            Real[] dest = new Real[columnsCount() + 1];
            System.arraycopy(rows[i], 0, dest, 0, columnsCount());
            dest[columnsCount()] = vector[i];
            matrix[i] = dest;
        }
        return Matrix.createInstance(matrix);
    }

    public Matrix inverse() {
        if (!isSquareMatrix()) {
            throw new IncompatibleMatrixException("Only square matrix has inverse");
        }
        Matrix b = augment(identityMatrix(rowsCount()));
        b.toReducedRowEchelonForm();
        if (!b.subMatrix(0, columnsCount()).isIdentityMatrix()) {
            throw new IncompatibleMatrixException("Matrix is not invertible");
        }
        return b.subMatrix(columnsCount(), columnsCount() << 1);
    }

    public Matrix add(Matrix matrix) {
        if (rowsCount() != matrix.rowsCount() || columnsCount() != matrix.columnsCount()) {
            throw new IncompatibleMatrixException("Different sized matrix cannot be added");
        }
        Matrix result = zeroMatrix(rowsCount(), columnsCount());
        for (int i = 0; i < rowsCount(); i++) {
            result.rows[i] = vectorsAdd(rows[i], matrix.rows[i]);
        }
        return result;
    }

    public Matrix multiply(Matrix matrix) {
        if (columnsCount() != matrix.rowsCount()) {
            throw new IncompatibleMatrixException("A*B must satisfies that 'columns count of A = rows count of B'");
        }
        Matrix result = zeroMatrix(rowsCount(), matrix.columnsCount());
        for (int r = 0; r < rowsCount(); r++) {
            for (int c = 0; c < columnsCount(); c++) {
                Real scalar = rows[r][c];
                Real[] scaledVector = matrix.multiplyRow(c, scalar);
                result.rows[r] = vectorsAdd(result.rows[r], scaledVector);
            }
        }
        return result;
    }

    public Matrix multiply(Rational scalar) {
        Matrix matrix = copy();
        for (int i = 0; i < rows.length; i++) {
            matrix.rows[i] = multiplyRow(i, scalar);
        }
        return matrix;
    }

    private Real[] multiplyRow(int rowIndex, Real scalar) {
        Real[] scaledRow = copyRow(rowIndex);
        for (int i = 0; i < scaledRow.length; i++) {
            scaledRow[i] = scaledRow[i].multiply(scalar);
        }
        return scaledRow;
    }

    private static Real[] vectorsAdd(Real[] a, Real[] b) {
        if (a.length != b.length) {
            throw new IncompatibleMatrixException("Different scaled vector cannot be added");
        }
        Real[] vector = new Real[a.length];
        for (int i = 0; i < a.length; i++) {
            vector[i] = a[i].add(b[i]);
        }
        return vector;
    }

    public int columnsCount() {
        return rows[0].length;
    }

    public int rowsCount() {
        return rows.length;
    }

    public Real[] copyRow(int rowIndex) {
        Real[] row = new Real[columnsCount()];
        for (int j = 0; j < columnsCount(); j++) {
            row[j] = this.rows[rowIndex][j].copy();
        }
        return row;
    }

    public Matrix copy() {
        Real[][] rows = new Real[rowsCount()][];
        for (int i = 0; i < rows.length; i++) {
            rows[i] = copyRow(i);
        }
        return createInstance(rows);
    }

    public Matrix minor(int i, int j) {
        Real[][] m = new Real[rowsCount() - 1][];
        int newR = 0;
        for (int r = 0; r < rowsCount(); r++) {
            if (r != i) {
                int newC = 0;
                m[newR] = new Real[columnsCount() - 1];
                for (int c = 0; c < columnsCount(); c++) {
                    if (c != j) {
                        m[newR][newC++] = rows[r][c].copy();
                    }
                }
                newR++;
            }
        }
        return Matrix.createInstance(m);
    }

    public Real determinant() {
        if (rowsCount() != columnsCount()) {
            throw new IncompatibleMatrixException("Only square matrix has determinant");
        }
        return innerDeterminant(this);
    }

    public boolean isSquareMatrix() {
        return columnsCount() == rowsCount();
    }

    public boolean isIdentityMatrix() {
        if (!isSquareMatrix()) return false;
        for (int r = 0; r < rowsCount(); r++) {
            for (int c = 0; c < columnsCount(); c++) {
                if (r == c) {
                    if (!get(r, c).equals(Rational.ONE)) return false;
                } else {
                    if (!get(r, c).equals(Rational.ZERO)) return false;
                }
            }
        }
        return true;
    }

    private static Real innerDeterminant(Matrix matrix) {
        if (matrix.rowsCount() == 1) {
            return matrix.rows[0][0];
        } else if (matrix.rowsCount() == 2) {
            return matrix.rows[0][0].multiply(matrix.rows[1][1])
                    .subtract(matrix.rows[1][0].multiply(matrix.rows[0][1]));
        } else {
            Real result = Rational.ZERO;
            for (int i = 0; i < matrix.columnsCount(); i++) {
                Real firstNumber = matrix.rows[0][i];
                Real coFactor = i % 2 == 0 ? firstNumber : firstNumber.negate();
                result = result.add(coFactor.multiply(innerDeterminant(matrix.minor(0, i))));
            }
            return result;
        }
    }

    private int rowPivotPosition(int row) {
        for (int i = 0; i < columnsCount(); i++) {
            if (!rows[row][i].equals(Rational.ZERO)) {
                return i;
            }
        }
        return -1;
    }

    private int findRowWithPivot(int beginRow, int pivotPos) {
        for (int i = beginRow; i < rowsCount(); i++) {
            if (!rows[i][pivotPos].equals(Rational.ZERO)) {
                return i;
            }
        }
        return -1;
    }

    void swapRows(int r1, int r2) {
        Real[] row1 = rows[r1];
        rows[r1] = rows[r2];
        rows[r2] = row1;
    }

    private Matrix subMatrix(int beginCol, int endCol) {
        Real[][] matrix = new Real[rowsCount()][];
        int width = endCol - beginCol;
        for (int r = 0; r < rowsCount(); r++) {
            Real[] column = new Real[width];
            System.arraycopy(rows[r], beginCol, column, 0, width);
            matrix[r] = column;
        }
        return createInstance(matrix);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Matrix)) {
            return false;
        }
        Matrix matrix = (Matrix) obj;
        if (rowsCount() != matrix.rowsCount() || columnsCount() != matrix.columnsCount()) {
            return false;
        }
        for (int i = 0; i < rowsCount(); i++) {
            for (int j = 0; j < columnsCount(); j++) {
                if (!rows[i][j].equals(matrix.rows[i][j])) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Real[] row : rows) {
            sb.append(Arrays.toString(row)).append('\n');
        }
        return sb.toString();
    }
}

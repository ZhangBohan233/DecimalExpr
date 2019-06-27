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

    public Real get(int row, int col) {
        return rows[row][col];
    }

    public void set(int row, int col, Real val) {
        rows[row][col] = val;
    }

    public Matrix toRREF() {
        return null;
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

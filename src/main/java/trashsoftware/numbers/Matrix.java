package trashsoftware.numbers;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

@SuppressWarnings("WeakerAccess")
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

    /**
     * Creates a matrix instance by directly putting numbers.
     * <p>
     * There are at most {@code rowsCount * colsCount} elements. If there aren't enough elements to fill the whole
     * matrix, 0's are filled.
     * For example, {@code createInstance(3, 2, Rational.ONE, Rational.ONE, Rational.TWO)} creates
     * [1 1]
     * [2 0]
     * [0 0]
     *
     * @param rowsCount number of rows (r)
     * @param colsCount number of columns (c)
     * @param elements  matrix elements, at most (r * c) elements.
     * @return the newly created matrix instance
     */
    public static Matrix createInstance(int rowsCount, int colsCount, Real... elements) {
        if (elements.length > rowsCount * colsCount)
            throw new IncompatibleMatrixException("Too many elements for matrix. ");
        Real[][] matrix = new Real[rowsCount][colsCount];
        for (int r = 0; r < rowsCount; r++) {
            for (int c = 0; c < colsCount; c++) {
                int i = r * colsCount + c;
                Real value;
                if (i < elements.length) value = elements[i];
                else value = Rational.ZERO;
                matrix[r][c] = value;
            }
        }
        return new Matrix(matrix);
    }

    public static Matrix fromDoubleMatrix(double[][] rows) {
        Real[][] matrix = new Real[rows.length][];
        for (int i = 0; i < rows.length; i++) {
            double[] iRow = rows[i];
            matrix[i] = new Real[iRow.length];
            for (int j = 0; j < iRow.length; j++) {
                Rational r = Rational.fromDouble(iRow[j]);
                matrix[i][j] = r;
            }
        }
        return new Matrix(matrix);
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

    public static Matrix integerMatrix(int[][] rows) {
        Real[][] matrix = new Real[rows.length][];
        for (int i = 0; i < rows.length; i++) {
            int[] iRow = rows[i];
            matrix[i] = new Real[iRow.length];
            for (int j = 0; j < iRow.length; j++) {
                Rational r = Rational.valueOf(iRow[j]);
                matrix[i][j] = r;
            }
        }
        return new Matrix(matrix);
    }

    public static Matrix ofVectors(Vector... columns) {
        int rowCount = columns[0].dimension();
        Real[][] matrix = new Real[rowCount][columns.length];
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columns.length; j++) {
                matrix[i][j] = columns[j].toArray()[i];
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

    public static Matrix oneMatrix(int dimension) {
        long[][] matrix = new long[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                matrix[i][j] = 1;
            }
        }
        return integerMatrix(matrix);
    }

    public Real get(int row, int col) {
        return rows[row][col];
    }

    public void set(int row, int col, Real val) {
        rows[row][col] = val;
    }

    /**
     * Reduce this matrix to Row-Echelon-Form.
     *
     * @return the scaling factor
     */
    public Real toRowEchelonForm() {
        int min = Math.min(rowsCount(), columnsCount());
        int theoPivotPos = 0;
        Real detFactor = Rational.ONE;
        for (int i = 0; i < min; i++) {  // pivot row
            int pivotPos = rowPivotPosition(i);
            if (theoPivotPos == columnsCount()) break;  // all remains are zero
            if (pivotPos != theoPivotPos) {
                int pivotRow = findRowWithPivot(i + 1, theoPivotPos);
                if (pivotRow == -1) {
                    theoPivotPos++;
                } else {
                    swapRows(i, pivotRow);
                    detFactor = detFactor.negate();
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
        return detFactor;
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

    public Real det() {
        if (!isSquareMatrix()) {
            throw new IncompatibleMatrixException("Only square matrix has determinant");
        }
        Matrix b = copy();
        Real factor = b.toRowEchelonForm();
        Real refDet = b.diagonal();
        return refDet.multiply(factor);
    }

    public Real diagonal() {
        if (!isSquareMatrix()) {
            throw new IncompatibleMatrixException("Only square matrix has main diagonal");
        }
        Real result = Rational.ONE;
        for (int i = 0; i < rowsCount(); i++) {
            result = result.multiply(get(i, i));
        }
        return result;
    }

    public Real trace() {
        if (!isSquareMatrix()) {
            throw new IncompatibleMatrixException("Only square matrix has main diagonal");
        }
        Real result = Rational.ZERO;
        for (int i = 0; i < rowsCount(); i++) {
            result = result.add(get(i, i));
        }
        return result;
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

    public Matrix augment(Vector vector) {
        return augment(vector.toArray());
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

    public Matrix subtract(Matrix matrix) {
        Matrix neg = matrix.multiply(Rational.valueOf(-1));
        return add(neg);
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

    public Vector multiply(Vector vector) {
        Real[] v = vector.toArray();
        if (v.length != columnsCount()) {
            throw new IncompatibleMatrixException("A*x must satisfies that 'columns count of A = dimension of x'");
        }
        Real[] result = new Real[rowsCount()];
        for (int i = 0; i < rows.length; i++) {
            Real rowRes = Rational.ZERO;

            for (int j = 0; j < v.length; j++) {
                rowRes = rowRes.add(get(i, j).multiply(v[j]));
            }
            result[i] = rowRes;
        }
        return Vector.createInstance(result);
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

    @Deprecated
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

    public boolean isZeroMatrix() {
        for (int r = 0; r < rowsCount(); r++) {
            for (int c = 0; c < columnsCount(); c++) {
                if (!get(r, c).equals(Rational.ZERO)) return false;
            }
        }
        return true;
    }

    public Matrix power(int n) {
        if (n <= 0) {
            throw new IncompatibleMatrixException("Matrix power must be positive integer");
        }
        Matrix p = copy();
        for (int i = 1; i < n; i++) {
            p = p.multiply(this);
        }
        return p;
    }

    public Matrix transpose() {
        Real[][] matrix = new Real[columnsCount()][rowsCount()];
        for (int r = 0; r < rowsCount(); r++) {
            for (int c = 0; c < columnsCount(); c++) {
                matrix[c][r] = get(r, c);
            }
        }
        return createInstance(matrix);
    }

    public Vector solve(Vector b) {
        Matrix inv = inverse();
        return inv.multiply(b);
    }

    public Vector solve(Real[] b) {
        Matrix inv = inverse();
        return inv.multiply(Vector.createInstance(b));
    }

    public int[] dimension() {
        return new int[]{rowsCount(), columnsCount()};
    }

    /**
     * Returns the nilpotent index k of this matrix A such that A^k = 0.
     * <p>
     * If this matrix is not nilpotent, returns -1.
     *
     * @return the nilpotent index k of this matrix A such that A^k = 0
     */
    public int nilpotentIndex() {
        Matrix p = copy();
        for (int i = 0; i < columnsCount() * rowsCount(); i++) {
            if (p.isZeroMatrix()) return i;
            p = p.multiply(this);
        }
        return -1;
    }

    public int rank() {
        Matrix b = copy();
        b.toRowEchelonForm();
        return b.numOfPivot();
    }

    public Vector column(int columnIndex) {
        Real[] v = new Real[rowsCount()];
        for (int r = 0; r < rowsCount(); r++) {
            v[r] = rows[r][columnIndex];
        }
        return Vector.createInstance(v);
    }

    public Matrix[] PLUDecomposition() {
        if (!isSquareMatrix()) {
            throw new IncompatibleMatrixException("Only square matrix can be factorized");
        }
        Matrix upper = copy();
        int min = rowsCount();
        int theoPivotPos = 0;
        List<Matrix> es = new ArrayList<>();
        Matrix p = identityMatrix(rowsCount());
        for (int i = 0; i < min; i++) {  // pivot row
            int pivotPos = upper.rowPivotPosition(i);
            if (theoPivotPos == columnsCount()) break;  // all remains are zero
            if (pivotPos != theoPivotPos) {
                int pivotRow = upper.findRowWithPivot(i + 1, theoPivotPos);
                if (pivotRow == -1) {
                    theoPivotPos++;
                } else {
                    upper.swapRows(i, pivotRow);
                    p.swapRows(i, pivotRow);
                }
                i--;
                continue;
            }
            Real pivot = upper.get(i, pivotPos);
            for (int j = i + 1; j < rowsCount(); j++) {  // rows under current pivot row
                Real rowFirst = upper.get(j, pivotPos);
                Real ratio = rowFirst.divide(pivot).negate();
                Matrix e = identityMatrix(rowsCount());
                for (int c = i; c < columnsCount(); c++) {
                    Real pivotRowMultiple = upper.get(i, c).multiply(ratio);
                    Real result = pivotRowMultiple.add(upper.get(j, c));
                    upper.set(j, c, result);

                    Real eRowMultiple = e.get(i, c).multiply(ratio);
                    e.set(j, c, eRowMultiple.add(e.get(j, c)));
                }
                es.add(e);
            }
        }
        Matrix lower = identityMatrix(rowsCount());
        for (Matrix e : es) {
            Matrix inverse = e.inverse();
            lower = lower.multiply(inverse);
        }
        return new Matrix[]{p.inverse(), lower, upper};
    }

    private int numOfPivot() {
        int pivots = 0;
        for (int r = 0; r < rowsCount(); r++) {
            for (int c = r; c < columnsCount(); c++) {
                if (!get(r, c).equals(Rational.ZERO)) {
                    pivots++;
                    break;
                }
            }
        }
        return pivots;
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

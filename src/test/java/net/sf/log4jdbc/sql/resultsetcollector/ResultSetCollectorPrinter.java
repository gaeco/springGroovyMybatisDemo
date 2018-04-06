/**
 * Copyright 2010 Tim Azzopardi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */


package net.sf.log4jdbc.sql.resultsetcollector;

import java.util.List;

/***
 * @author Tim Azzopardi
 * @author Mathieu Seppey
 *
 * Update : changed printResultSet into getResultSetToPrint
 *
 */

public class ResultSetCollectorPrinter {

    /**
     * A StringBuffer which is used to build the formatted table to print
     */
    private StringBuffer table = new StringBuffer();
    private static final int MAX_ROWS_PRINTABLE = 100;
    private static final int MAX_COL_SPACE = 100;

    /**
     * Default constructor
     */
    public ResultSetCollectorPrinter() {

    }

    /***
     * Return a table which represents a <code>ResultSet</code>,
     * to be printed by a logger,
     * based on the content of the provided <code>resultSetCollector</code>.
     *
     * This method will be actually called by a <code>SpyLogDelegator</code>
     * when the <code>next()</code> method of the spied <code>ResultSet</code>
     * return <code>false</code> meaning that its end is reached.
     * It will be also called if the <code>ResultSet</code> is closed.
     *
     *
     * @param resultSetCollector the ResultSetCollector which has collected the data we want to print
     * @return A <code>String</code> which contains the formatted table to print
     *
     * @see net.sf.log4jdbc.sql.resultsetcollector.DefaultResultSetCollector
     * @see net.sf.log4jdbc.log.SpyLogDelegator
     *
     */
    public String getResultSetToPrint(ResultSetCollector resultSetCollector) {
        this.table.append(System.getProperty("line.separator"));
        if(resultSetCollector != null && resultSetCollector.getRows() != null){
            this.table.append("rows : "+resultSetCollector.getRows().size());
        }

        this.table.append(System.getProperty("line.separator"));

        int columnCount = resultSetCollector.getColumnCount();
        int maxLength[] = new int[columnCount];
        //칼럼의 최대 길기 구하기
        //칼럼 헤더와 값을 모두 열어서 길이를 확인한다.
        //한글인 경우 2자리로 계산한다.
        List<List<Object>> rows = resultSetCollector.getRows();
        if(rows != null){
            adjustStr(rows);
            for (int column = 1; column <= columnCount; column++) {
                int maxCharCnt = countLength(resultSetCollector.getColumnName(column));
                for(int i = 0; i < rows.size() && i < MAX_ROWS_PRINTABLE;i++){
                    List<Object> row = rows.get(i);
                    int length = countLength(row.get(column -1).toString());
//                System.out.println(" row.get(" +(column -1)+").toString() :" +row.get(column -1).toString() +" / length : "+length);
                    maxCharCnt = Math.max(maxCharCnt, length);
                }
                maxLength[column - 1] = maxCharCnt;
            }
            if (resultSetCollector.getRows() != null) {
                for (List<Object> printRow : resultSetCollector.getRows()) {
                    int colIndex = 0;
                    for (Object v : printRow) {
                        if (v != null) {
                            int length = v.toString().length();
                            if (length > maxLength[colIndex]) {
                                maxLength[colIndex] = length;
                            }
                        }
                        colIndex++;
                    }
                }
            }
            for (int column = 1; column <= columnCount; column++) {
                maxLength[column - 1] = maxLength[column - 1] + 1;
            }
            //첫줄 출력 : +----------+-----------+
            this.table.append("+");

            for (int column = 1; column <= columnCount; column++) {
                this.table.append(padRight("-", maxLength[column - 1]).replaceAll(" ", "-") + "+");
            }
            this.table.append(System.getProperty("line.separator"));
            this.table.append("|");//column 헤더 출력 : |column1  |column2 |
            for (int column = 1; column <= columnCount; column++) {
                this.table.append(padRight(resultSetCollector.getColumnName(column), maxLength[column - 1]) + "|");
            }
            this.table.append(System.getProperty("line.separator"));
            this.table.append("+");//column 헤더 아랫줄 출력 : +----------+-----------+
            for (int column = 1; column <= columnCount; column++) {
                this.table.append(padRight("-", maxLength[column - 1]).replaceAll(" ", "-") + "+");
            }
            this.table.append(System.getProperty("line.separator"));
            if (resultSetCollector.getRows() != null) {//row 별 데이터 출력
                int cnt = 0;
                for (List<Object> printRow : resultSetCollector.getRows()) {
                    int colIndex = 0;
                    this.table.append("|");
                    for (Object v : printRow) {
                        this.table.append(padRight(v == null ? "(null)" : v.toString(), maxLength[colIndex]) + "|");
                        colIndex++;
                    }
                    this.table.append(System.getProperty("line.separator"));
                    if(++cnt >= MAX_ROWS_PRINTABLE) //최대 출력치를 벗어나면 더 이상 출력하지 않는다.
                        break;
                }
            }
            this.table.append("+");
            for (int column = 1; column <= columnCount; column++) {
                this.table.append(padRight("-", maxLength[column - 1]).replaceAll(" ", "-") + "+");
            }
        }

        this.table.append(System.getProperty("line.separator"));

        resultSetCollector.reset();

        return this.table.toString() ;

    }

    /***
     * Add space to the provided <code>String</code> to match the provided width
     * @param s the <code>String</code> we want to adjust
     * @param n the width of the returned <code>String</code>
     * @return a <code>String</code> matching the provided width
     */
    private static String padRight(String s, int n) {
        n = n - (countLength(s) - s.length());//한글이 있는 경우 다시 글수를 줄여준다.
        return String.format("%1$-" + n + "s", s);
    }


    private static int countLength(String str){
        if(str == null){
            return "(null)".length();
        }
        int cnt = 0;

        for(char c : str.toCharArray()){
            if(c > 256){ //한글일 경우 두 칸으로 간주한다.
                cnt = cnt + 2;
            }else{
                cnt++;
            }
        }

        return cnt;
    }

    /**
     * 개행문자를 \r\n형태의 문자열로 바꿔주고 최대 문자열이 넘어갈 경우 ...축약시켜주는 함수.
     **/
    private static void adjustStr(List<List<Object>> rows){
        int cnt = 0;
        if(rows != null){
            for(List<Object> row : rows){
                int idx = 0;
                for(Object obj : row){
                    if(obj != null && obj instanceof String){
                        String s = (String)obj;
                        int charCnt = 0;
                        StringBuffer sb = new StringBuffer();
                        for(char c : s.toCharArray()){
                            if(c == '\r'){
                                sb.append("\\r");
                                charCnt++;
                            }else if(c == '\n'){
                                sb.append("\\n");
                                charCnt++;
                            }else{
                                sb.append(c);
                            }
                            if(c > 256){ //한글일 경우 두 칸으로 간주한다.
                                charCnt++;
                            }
                            charCnt++;

                            if(charCnt == MAX_COL_SPACE - 3){
                                sb.append("...");
                                break;
                            }else if(charCnt >= MAX_COL_SPACE - 2){
                                sb.append("..");
                                break;
                            }
                        }//..s.toCharArray()
                        row.set(idx, sb.toString());
                    }
                    idx++;
                }//..row
                if(++cnt >= MAX_ROWS_PRINTABLE) //최대 출력치를 벗어나면 더 이상 출력하지 않는다.
                    break;
            }//..rows

        }

    }

}

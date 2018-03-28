package com.example.mypc.a15puzzlegametechkids;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Set;

import static java.lang.Math.abs;

/**
 * Created by bùm on 3/28/2018.
 */

class COORD {
    public int X, Y;

    public COORD(int X, int Y) {
        this.X = X;
        this.Y = Y;
    }
}

class state {
    public int[][] table = new int[4][4];
    public int cost;
    public COORD pos0;
    public String lastMove = "";

    public state() {
    }

    public state(int[][] table, int cost, COORD pos0, String lastMove) {
        this.table = table;
        this.cost = cost;
        this.pos0 = pos0;
        this.lastMove = lastMove;
    }
}

public class SolvingPuzzle {

    static Comparator<state> comparator = new StateComparator();
    static PriorityQueue<state> heap = new PriorityQueue<state>(1000000, comparator);

    static Set<int[][]> visited;

    public static String solving(int[][] table) {
        state node = new state(table, 0, locate(0, table), "");

        //complete first row and first column
        restruct(node);

        //A* algorithm
        heap.add(node);
        visited.add(node.table);

        while (true) {
            node = heap.poll();

            neighbor(node);

            if (StateComparator.HeuristicCost(node) == 0) break;
        }

        //empty data
        visited.clear();
        heap.clear();

        //optimize result
        String result = node.lastMove;

        do {
            node.lastMove = result;
            result = "";
            for (int i = 0; i < node.lastMove.length(); i++) {
                if (i < node.lastMove.length()-1) {
                    if (node.lastMove.charAt(i) == 'R' && node.lastMove.charAt(i+1) == 'L') continue;
                    if (node.lastMove.charAt(i) == 'D' && node.lastMove.charAt(i+1) == 'U') continue;
                    if (node.lastMove.charAt(i) == 'L' && node.lastMove.charAt(i+1) == 'R') continue;
                    if (node.lastMove.charAt(i) == 'U' && node.lastMove.charAt(i+1) == 'D') continue;
                }
                if (i > 0) {
                    if (node.lastMove.charAt(i-1) == 'R' && node.lastMove.charAt(i) == 'L') continue;
                    if (node.lastMove.charAt(i-1) == 'D' && node.lastMove.charAt(i) == 'U') continue;
                    if (node.lastMove.charAt(i-1) == 'L' && node.lastMove.charAt(i) == 'R') continue;
                    if (node.lastMove.charAt(i-1) == 'U' && node.lastMove.charAt(i) == 'D') continue;
                }
                result += node.lastMove.charAt(i);
            }
        } while (!result.equals(node.lastMove));

        return result;
    }

    private static void neighbor(state node) {
        int pivot;
        state temp;
        if (node.pos0.X > 0) {
            temp = node;
            temp.pos0.X--;
            temp.lastMove += "D";
            pivot = temp.table[node.pos0.X][node.pos0.Y];
            temp.table[node.pos0.X][node.pos0.Y] = temp.table[temp.pos0.X][temp.pos0.Y];
            temp.table[temp.pos0.X][temp.pos0.Y] = pivot;
            temp.cost++;

            if (!visited.contains(temp.table)) {
                heap.add(temp);
                visited.add(temp.table);
            }
        }

        if (node.pos0.X < 4 - 1) {
            temp = node;
            temp.pos0.X++;
            temp.lastMove += "U";
            pivot = temp.table[node.pos0.X][node.pos0.Y];
            temp.table[node.pos0.X][node.pos0.Y] = temp.table[temp.pos0.X][temp.pos0.Y];
            temp.table[temp.pos0.X][temp.pos0.Y] = pivot;
            temp.cost++;

            if (!visited.contains(temp.table)) {
                heap.add(temp);
                visited.add(temp.table);
            }
        }

        if (node.pos0.Y > 0) {
            temp = node;
            temp.pos0.Y--;
            temp.lastMove += "R";
            pivot = temp.table[node.pos0.X][node.pos0.Y];
            temp.table[node.pos0.X][node.pos0.Y] = temp.table[temp.pos0.X][temp.pos0.Y];
            temp.table[temp.pos0.X][temp.pos0.Y] = pivot;
            temp.cost++;

            if (!visited.contains(temp.table)) {
                heap.add(temp);
                visited.add(temp.table);
            }
        }

        if (node.pos0.Y < 4 - 1) {
            temp = node;
            temp.pos0.Y++;
            temp.lastMove += "L";
            pivot = temp.table[node.pos0.X][node.pos0.Y];
            temp.table[node.pos0.X][node.pos0.Y] = temp.table[temp.pos0.X][temp.pos0.Y];
            temp.table[temp.pos0.X][temp.pos0.Y] = pivot;
            temp.cost++;

            if (!visited.contains(temp.table)) {
                heap.add(temp);
                visited.add(temp.table);
            }
        }

    }

    private static COORD locate(int value, int[][] table) {
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                if (table[i][j] == value)
                    return new COORD(i, j);
        return null;
    }

    private static void restruct(state node) {
        COORD target;
        //move 1
        if (locate(1, node.table).X != 0) {
            while (node.pos0.X != locate(1, node.table).X - 1)
                if (node.pos0.X < locate(1, node.table).X - 1) moveBlock("U", node);
                else moveBlock("D", node);
            while (node.pos0.Y != locate(1, node.table).Y)
                if (node.pos0.Y < locate(1, node.table).Y) moveBlock("L", node);
                else moveBlock("R", node);
            while (locate(1, node.table).X != 0) {
                moveBlock("U", node);
                if (locate(1, node.table).X != 0)
                    if (locate(1, node.table).Y != 3) moveBlock("LDDR", node);
                    else moveBlock("RDDL", node);
            }
        }

        if (locate(1, node.table).Y != 0) {
            while (node.pos0.Y != locate(1, node.table).Y - 1)
                if (node.pos0.Y < locate(1, node.table).Y - 1) moveBlock("L", node);
                else moveBlock("R", node);
            while (node.pos0.X != locate(1, node.table).X)
                moveBlock("D", node);
            while (locate(1, node.table).Y != 0) {
                moveBlock("L", node);
                if (locate(1, node.table).Y != 0) moveBlock("URRD", node);
            }
        }
        //end 1

        //move 2
        if (locate(2, node.table).Y != 1) {
            if (locate(2, node.table).Y == 0) target = new COORD(locate(2, node.table).X, 1);
            else target = new COORD(locate(2, node.table).X, locate(2, node.table).Y - 1);
            while (node.pos0.Y != target.Y)
                if (node.pos0.Y < target.Y) moveBlock("L", node);
                else moveBlock("R", node);
            while (node.pos0.X != target.X)
                if (node.pos0.X < target.X) moveBlock("U", node);
                else moveBlock("D", node);
            while (locate(2, node.table).Y != 1) {
                if (node.pos0.Y > locate(2, node.table).Y) moveBlock("R", node);
                else moveBlock("L", node);
                if (locate(2, node.table).Y != 1)
                    if (locate(2, node.table).X != 3) moveBlock("URRD", node);
                    else moveBlock("DRRU", node);
            }
        }

        if (locate(2, node.table).X != 0) {
            if (node.pos0.X == locate(2, node.table).X)
                if (node.pos0.X != 3) moveBlock("U", node);
                else moveBlock("D", node);
            while (node.pos0.Y != locate(2, node.table).Y)
                if (node.pos0.Y < locate(2, node.table).Y) moveBlock("L", node);
                else moveBlock("R", node);
            while (node.pos0.X != locate(2, node.table).X - 1)
                if (node.pos0.X < locate(2, node.table).X - 1) moveBlock("U", node);
                else moveBlock("D", node);
            while (locate(2, node.table).X != 0) {
                moveBlock("U", node);
                if (locate(2, node.table).X != 0) moveBlock("LDDR", node);
            }
        }
        //end 2


        //move 4
        if (node.pos0.X == 0) moveBlock("U", node);
        if (locate(4, node.table).Y != 2) {
            if (locate(4, node.table).Y < 2)
                target = new COORD(locate(4, node.table).X, locate(4, node.table).Y + 1);
            else target = new COORD(locate(4, node.table).X, 2);
            while (node.pos0.Y != target.Y)
                if (node.pos0.Y < target.Y) moveBlock("L", node);
                else moveBlock("R", node);
            while (node.pos0.X != target.X)
                if (node.pos0.X < target.X) moveBlock("U", node);
                else moveBlock("D", node);
            while (locate(4, node.table).Y != 2) {
                if (node.pos0.Y > locate(4, node.table).Y) moveBlock("R", node);
                else moveBlock("L", node);
                if (locate(4, node.table).Y != 2)
                    if (locate(4, node.table).X != 3) moveBlock("ULLD", node);
                    else moveBlock("DLLU", node);
            }
        }

        if (locate(4, node.table).X != 0) {
            if (node.pos0.X == locate(4, node.table).X)
                if (node.pos0.X != 3) moveBlock("U", node);
                else moveBlock("D", node);
            while (node.pos0.Y != locate(4, node.table).Y)
                if (node.pos0.Y < locate(4, node.table).Y) moveBlock("L", node);
                else moveBlock("R", node);
            while (node.pos0.X != locate(4, node.table).X - 1)
                if (node.pos0.X < locate(4, node.table).X - 1) moveBlock("U", node);
                else moveBlock("D", node);
            while (locate(4, node.table).X != 0) {
                moveBlock("U", node);
                if (locate(4, node.table).X != 0) moveBlock("LDDR", node);
            }
        }
        //end 4

        //move 3
        if (node.pos0.X == 0) moveBlock("U", node);
        if (node.table[0][3] == 3) {
            if (node.pos0.Y != 3) moveBlock("L", node);
            moveBlock("DRUULDRULDRDLURU", node);
        }

        if (locate(3, node.table).Y != 2) {
            if (locate(3, node.table).Y < 2)
                target = new COORD(locate(3, node.table).X, locate(3, node.table).Y + 1);
            else target = new COORD(locate(3, node.table).X, 2);
            while (node.pos0.Y != target.Y)
                if (node.pos0.Y < target.Y) moveBlock("L", node);
                else moveBlock("R", node);
            while (node.pos0.X != target.X)
                if (node.pos0.X < target.X) moveBlock("U", node);
                else moveBlock("D", node);
            while (locate(3, node.table).Y != 2) {
                if (node.pos0.Y > locate(3, node.table).Y) moveBlock("R", node);
                else moveBlock("L", node);
                if (locate(3, node.table).Y != 2)
                    if (locate(3, node.table).X != 3) moveBlock("ULLD", node);
                    else moveBlock("DLLU", node);
            }
        }

        if (locate(3, node.table).X != 1) {
            if (node.pos0.X == locate(3, node.table).X)
                if (node.pos0.X != 3) moveBlock("U", node);
                else moveBlock("D", node);
            while (node.pos0.Y != locate(3, node.table).Y)
                if (node.pos0.Y < locate(3, node.table).Y) moveBlock("L", node);
                else moveBlock("R", node);
            while (node.pos0.X != locate(3, node.table).X - 1)
                if (node.pos0.X < locate(3, node.table).X - 1) moveBlock("U", node);
                else moveBlock("D", node);
            while (locate(3, node.table).X != 1) {
                moveBlock("U", node);
                if (locate(3, node.table).X != 1) moveBlock("LDDR", node);
            }
        }
        //end 3

        //finish first row
        if (node.pos0.X == 1) moveBlock("U", node);
        while (node.pos0.Y != 3) moveBlock("L", node);
        while (node.pos0.X != 0) moveBlock("D", node);
        moveBlock("RU", node);
        //

        //move 5
        if (locate(5, node.table).X != 1) {
            while (node.pos0.X < locate(5, node.table).X - 1) moveBlock("U", node);
            while (node.pos0.Y != locate(5, node.table).Y)
                if (node.pos0.Y < locate(5, node.table).Y) moveBlock("L", node);
                else moveBlock("R", node);
            while (locate(5, node.table).X != 1) {
                moveBlock("U", node);
                if (locate(5, node.table).X != 1)
                    if (locate(5, node.table).Y != 3) moveBlock("LDDR", node);
                    else moveBlock("RDDL", node);
            }
        }

        if (locate(5, node.table).Y != 0) {
            if (locate(5, node.table).Y == node.pos0.Y)
                if (node.pos0.Y != 3) moveBlock("L", node);
                else moveBlock("R", node);
            while (node.pos0.X != 1) moveBlock("D", node);
            while (node.pos0.Y != locate(5, node.table).Y - 1)
                if (node.pos0.Y < locate(5, node.table).Y - 1) moveBlock("L", node);
                else moveBlock("R", node);
            while (locate(5, node.table).Y != 0) {
                moveBlock("L", node);
                if (locate(5, node.table).Y != 0) moveBlock("URRD", node);
            }
        }
        //end 5

        //move 13
        if (node.pos0.Y == 0) moveBlock("L", node);
        if (locate(13, node.table).X != 2) {
            target = new COORD(2, locate(13, node.table).Y);
            while (node.pos0.X != target.X)
                if (node.pos0.X < target.X) moveBlock("U", node);
                else moveBlock("D", node);
            while (node.pos0.Y != target.Y)
                if (node.pos0.Y < target.Y) moveBlock("L", node);
                else moveBlock("R", node);
            if (locate(13, node.table).X != 2)
                if (node.pos0.X > locate(13, node.table).X) moveBlock("D", node);
                else moveBlock("U", node);
        }

        if (locate(13, node.table).Y != 0) {
            if (node.pos0.Y == locate(13, node.table).Y)
                if (node.pos0.Y != 3) moveBlock("L", node);
                else moveBlock("R", node);
            while (node.pos0.X != locate(13, node.table).X)
                if (node.pos0.X < locate(13, node.table).X) moveBlock("U", node);
                else moveBlock("D", node);
            while (node.pos0.Y != locate(13, node.table).Y - 1)
                if (node.pos0.Y < locate(13, node.table).Y - 1) moveBlock("L", node);
                else moveBlock("R", node);
            while (locate(13, node.table).Y != 0) {
                moveBlock("L", node);
                if (locate(13, node.table).Y != 0) moveBlock("URRD", node);
            }
        }
        //end 13

        //move 9
        if (node.pos0.Y == 0) moveBlock("L", node);
        if (node.table[3][0] == 9) {
            if (node.pos0.X != 3) moveBlock("U", node);
            moveBlock("RDLLURDLURDRULDL", node);
        }

        if (locate(9, node.table).X != 2) {
            target = new COORD(2, locate(9, node.table).Y);
            while (node.pos0.X != target.X)
                if (node.pos0.X < target.X) moveBlock("U", node);
                else moveBlock("D", node);
            while (node.pos0.Y != target.Y)
                if (node.pos0.Y < target.Y) moveBlock("L", node);
                else moveBlock("R", node);
            if (locate(9, node.table).X != 2)
                if (node.pos0.X > locate(9, node.table).X) moveBlock("D", node);
                else moveBlock("U", node);
        }

        if (locate(9, node.table).Y != 1) {
            if (node.pos0.Y == locate(9, node.table).Y)
                if (node.pos0.Y != 3) moveBlock("L", node);
                else moveBlock("R", node);
            while (node.pos0.X != locate(9, node.table).X)
                if (node.pos0.X < locate(9, node.table).X) moveBlock("U", node);
                else moveBlock("D", node);
            while (node.pos0.Y != locate(9, node.table).Y - 1)
                if (node.pos0.Y < locate(9, node.table).Y - 1) moveBlock("L", node);
                else moveBlock("R", node);
            while (locate(9, node.table).Y != 1) {
                moveBlock("L", node);
                if (locate(9, node.table).Y != 1) moveBlock("URRD", node);
            }
        }
        //end 9

        //finish first column
        if (node.pos0.Y == 1) moveBlock("L", node);
        while (node.pos0.X != 3) moveBlock("U", node);
        while (node.pos0.Y != 0) moveBlock("R", node);
        moveBlock("DL", node);
        //
    }

    private static void moveBlock(String moves, state node) {
        int temp;

        for (int i = 0; i < moves.length(); i++) {
            node.lastMove += moves.charAt(i);

            if (moves.charAt(i) == 'L') {
                temp = node.table[node.pos0.X][node.pos0.Y];
                node.table[node.pos0.X][node.pos0.Y] = node.table[node.pos0.X][node.pos0.Y + 1];
                node.table[node.pos0.X][node.pos0.Y + 1] = temp;
                node.pos0.Y++;
            }

            if (moves.charAt(i) == 'R') {
                temp = node.table[node.pos0.X][node.pos0.Y];
                node.table[node.pos0.X][node.pos0.Y] = node.table[node.pos0.X][node.pos0.Y - 1];
                node.table[node.pos0.X][node.pos0.Y - 1] = temp;
                node.pos0.Y--;
            }

            if (moves.charAt(i) == 'U') {
                temp = node.table[node.pos0.X][node.pos0.Y];
                node.table[node.pos0.X][node.pos0.Y] = node.table[node.pos0.X + 1][node.pos0.Y];
                node.table[node.pos0.X + 1][node.pos0.Y] = temp;
                node.pos0.X++;
            }

            if (moves.charAt(i) == 'D') {
                temp = node.table[node.pos0.X][node.pos0.Y];
                node.table[node.pos0.X][node.pos0.Y] = node.table[node.pos0.X - 1][node.pos0.Y];
                node.table[node.pos0.X - 1][node.pos0.Y] = temp;
                node.pos0.X--;
            }
        }
    }

}

class StateComparator implements Comparator<state> {
    @Override
    public int compare(state o1, state o2) {
        if (o1.cost + HeuristicCost(o1) >= o2.cost + HeuristicCost(o2)) return 1;
        else return 0;
    }

    public static int HeuristicCost(state node) {
        int res = 0;

        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                if (node.table[i][j] != 0) {
                    int temp = abs(i - (node.table[i][j] - 1) / 4) + abs(j - (node.table[i][j] - 1) % 4);
                    if (node.table[i][j] == 1 || node.table[i][j] == 2 ||
                            node.table[i][j] == 3 || node.table[i][j] == 4 ||
                            node.table[i][j] == 5 || node.table[i][j] == 9 ||
                            node.table[i][j] == 13) temp *= 1000;
                    res += temp;
                }

        return res;
    }
}

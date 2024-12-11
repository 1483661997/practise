package DataStruct.BASE.Graph;

import java.util.*;

class Equation {
    String x;
    String y;

    public Equation(String x, String y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equation equation = (Equation) o;
        return Objects.equals(x, equation.x) && Objects.equals(y, equation.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}



class Solution {
    HashMap<String, Set<String>> map;
    HashMap<Equation, Double> map1;
    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        int len = queries.size();
        double[] res = new double[len];
        map = new HashMap<>();
        map1 = new HashMap<>();
        int len1 = values.length;

        for(int i = 0; i < len1; i++){
            String a = equations.get(i).get(0);
            String b = equations.get(i).get(1);

            map1.put(new Equation(a, b), values[i]);
            map1.put(new Equation(b,a), 1.0/values[i]);
            Set<String> set1 =   map.getOrDefault(a, new HashSet<>());
            Set<String> set2 =   map.getOrDefault(b, new HashSet<>());
            set1.add(b);
            set2.add(a);
            map.put(a,set1);
            map.put(b,set2);
        }


        for (int i = 0; i < len; i++) {
            String a = queries.get(i).get(0);
            String b = queries.get(i).get(1);
            if (!map.containsKey(a) || !map.containsKey(b)) {
                res[i] = -1.0;
            } else {
                res[i] = dfs(a, b, new HashSet<String>());
                if (res[i] != -1.0) {
                    map1.put(new Equation(a, b), res[i]);  // Cache the result
                    map1.put(new Equation(b, a), 1 / res[i]);  // Cache the inverse
                }
            }
        }



        return res;

    }

    private double dfs(String a, String b, HashSet<String> visited) {
        // direct match found
        if (a.equals(b)) {
            return 1.0;
        }
        visited.add(a);
        if (!map.containsKey(a)) {
            return -1.0;
        }
        for (String neighbor : map.get(a)) {
            if (!visited.contains(neighbor)) {
                double answer = dfs(neighbor, b, visited);
                if (answer != -1.0) {
                    return answer * map1.get(new Equation(a, neighbor));
                }
            }
        }
        return -1.0;
    }



}
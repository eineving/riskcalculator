import java.util.*;

/**
 * Created by Daniel on 2016-03-29.
 */
public class TreeOutcome {
    HashMap<Integer, List<Node>> nodes = new HashMap<>();
    Node root;
    int nbrOfNodes;

    //debugs
    int debugMatchingA, debugMatchingB, debugMatchingC, debugParentSearch;

    public TreeOutcome(int attackers, int defenders) {
        for (int i = 0; i <= attackers + defenders; i++) {
            nodes.put(i, new LinkedList<>());
        }

        root = new Node(attackers, defenders);
        nbrOfNodes++;
        root.percentage = 1.0;
        nodes.get(attackers + defenders).add(root);
        createChildren(root);
        calculatePercentage();

    }

    public void createChildren(Node parent) {
        if (parent.attackers == 0 || parent.defenders == 0) {
            return;
        } else if (parent.attackers > 1 && parent.defenders > 1) { //Three possible outcomes
            Node aWon = getMatchingNode(parent.attackers, parent.defenders - 2);
            Node split = getMatchingNode(parent.attackers - 1, parent.defenders - 1);
            Node dWon = getMatchingNode(parent.attackers - 2, parent.defenders);

            //5 or 4 dice
            if (parent.attackers > 2) {//5 dice
                aWon.addParent(parent, 2890.0 / 7776.0, Edge.A2);
                dWon.addParent(parent, 2275.0 / 7776.0, Edge.SPLIT);
                split.addParent(parent, 2611.0 / 7776.0, Edge.D2);
            } else {//4 dice
                aWon.addParent(parent, 295.0 / 1296.0, Edge.A2);
                dWon.addParent(parent, 581.0 / 1296.0, Edge.SPLIT);
                split.addParent(parent, 420.0 / 1296.0, Edge.D2);
            }

            createChildren(aWon);
            createChildren(dWon);
            createChildren(split);
        } else if (parent.attackers == 1 || parent.defenders == 1) {
            Node aWon = getMatchingNode(parent.attackers, parent.defenders - 1);
            Node dWon = getMatchingNode(parent.attackers - 1, parent.defenders);

            if (parent.defenders == 1) {
                if (parent.attackers > 2) {
                    aWon.addParent(parent, 855.0 / 1296.0, Edge.A1);
                    dWon.addParent(parent, 441.0 / 1296.0, Edge.D1);
                } else if (parent.attackers == 2) {
                    aWon.addParent(parent, 125.0 / 216.0, Edge.A1);
                    dWon.addParent(parent, 91.0 / 216.0, Edge.D1);
                } else if (parent.attackers == 1) {
                    aWon.addParent(parent, 15.0 / 36.0, Edge.A1);
                    dWon.addParent(parent, 21.0 / 36.0, Edge.D1);
                }
            } else { //2 defender dice, 1 attacker
                aWon.addParent(parent, 55.0 / 216.0, Edge.A1);
                dWon.addParent(parent, 161.0 / 216.0, Edge.D1);
            }
            createChildren(aWon);
            createChildren(dWon);
        }
    }

    private Node getMatchingNode(int attackers, int defenders) {
        debugMatchingC++;
        for (Node temp : nodes.get(attackers + defenders)) {
            debugMatchingA++;
            if (temp.attackers == attackers && temp.defenders == defenders) {
                debugMatchingB++;
                return temp;
            }
        }
        nbrOfNodes++;
        Node node = new Node(attackers, defenders);
        nodes.get(attackers + defenders).add(node);
        return node;
    }

    public Outcome getOutcome() {
        Outcome outcome = new Outcome(root.attackers, root.defenders);
        nodes.forEach((i, list) -> {
            for (Node node : list) {
                if (node.attackers == 0 || node.defenders == 0) {
                    outcome.add(node.attackers, node.defenders, node.percentage);
                }
            }
        });
        return outcome;
    }

    private void calculatePercentage() {
        for (int i = root.attackers + root.defenders; i > 0; i--) {
            for (Node node : nodes.get(i)) {
                node.calculatePercentage();
            }
        }
    }


    private class Node {
        public int attackers, defenders;

        public Node aWon, dWon, split;
        public double awp, dwp, splitp;
        public double percentage;

        public Map<Node, Double> parents = new HashMap<>();


        public Node(int attackers, int defenders) {
            this.attackers = attackers;
            this.defenders = defenders;
            percentage = 0;
        }

        public void addParent(Node parent, double edgePercentage, Edge edge) {
            for (Node temp : parents.keySet()) {
                debugParentSearch++;
                if (parent.equals(temp)) {
                    return;
                }
            }
            //System.out.println("Added new parent");
            parents.put(parent, edgePercentage);
            /*if (aWon == null && (edge.equals(Edge.A1) || edge.equals(Edge.A2))) {
                aWon = parent;
                awp = edgePercentage;
            } else if (dWon == null && (edge.equals(Edge.A1) || edge.equals(Edge.A2))) {
                dWon= parent;
                dwp = edgePercentage;
            } else if (split == null) {
                splitp
            }
            debugParentSearch++;
            */
        }

        public void calculatePercentage() {
            parents.forEach((parent, edgePercentage) -> {
                percentage += parent.percentage * edgePercentage;
            });

        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Node node = (Node) o;

            if (attackers != node.attackers) return false;
            return defenders == node.defenders;

        }

        @Override
        public int hashCode() {
            int result = attackers;
            result = 31 * result + defenders;
            return result;
        }

    }

    public enum Edge {
        A1, A2, SPLIT, D2, D1
    }
}

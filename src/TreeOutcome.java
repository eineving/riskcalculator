import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Daniel on 2016-03-29.
 */
public class TreeOutcome {
    List<Node> nodes = new ArrayList<>();
    Node root;

    public TreeOutcome(int attackers, int defenders) {
        root = new Node(attackers, defenders);
        root.percentage = 1.0;
        createChildren(root);

    }

    public void createChildren(Node parent) {
        if (parent.attackers == 0 || parent.defenders == 0) {
            return;
        } else if (parent.attackers > 1 && parent.defenders > 1) { //Three possible outcomes
            Node aWon = getMatchingNode(parent.attackers, parent.defenders - 2);
            Node dWon = getMatchingNode(parent.attackers - 2, parent.defenders);
            Node split = getMatchingNode(parent.attackers - 1, parent.defenders - 1);

            //5 or 4 dice
            if (parent.attackers > 2) {//5 dice
                aWon.addParent(parent, 2890.0 / 7776.0);
                dWon.addParent(parent, 2275.0 / 7776.0);
                split.addParent(parent, 2611.0 / 7776.0);
            } else {//4 dice
                aWon.addParent(parent, 295.0 / 1296.0);
                dWon.addParent(parent, 581.0 / 1296.0);
                split.addParent(parent, 420.0 / 1296.0);
            }

            createChildren(aWon);
            createChildren(dWon);
            createChildren(split);
        } else if (parent.attackers == 1 || parent.defenders == 1) {
            Node aWon = getMatchingNode(parent.attackers, parent.defenders - 1);
            Node dWon = getMatchingNode(parent.attackers - 1, parent.defenders);

            if (parent.defenders == 1) {
                if (parent.attackers > 2) {
                    aWon.addParent(parent, 855.0 / 1296.0);
                    dWon.addParent(parent, 441.0 / 1296.0);
                } else if (parent.attackers == 2) {
                    aWon.addParent(parent, 125.0 / 216.0);
                    dWon.addParent(parent, 91.0 / 216.0);
                } else if (parent.attackers == 1) {
                    aWon.addParent(parent, 15.0 / 36.0);
                    dWon.addParent(parent, 21.0 / 36.0);
                }
            } else { //2 defender dice, 1 attacker
                aWon.addParent(parent, 55.0 / 216.0);
                dWon.addParent(parent, 161.0 / 216.0);
            }
            createChildren(aWon);
            createChildren(dWon);
        }
    }

    private Node getMatchingNode(int attackers, int defenders) {
        for (Node temp : nodes) {
            if (temp.attackers == attackers && temp.defenders == defenders) {
                return temp;
            }
        }
        Node node = new Node(attackers, defenders);
        nodes.add(node);
        return node;
    }


    private class Node {
        public int attackers, defenders;

        public double percentage;

        public List<Node> parents = new LinkedList<Node>();

        public Node(int attackers, int defenders) {
            this.attackers = attackers;
            this.defenders = defenders;
            percentage = 0;
        }

        public void addParent(Node parent, double edgePercentage) {
            parents.add(parent);
            percentage += parent.percentage * edgePercentage;
        }
    }
}

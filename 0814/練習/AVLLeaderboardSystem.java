
import java.util.*;

/**
 * AVL 樹應用 - 排行榜系統練習 練習目標：設計遊戲排行榜系統，支援添加、更新、查詢排名等操作
 */
class Player {

    String name;
    int score;

    public Player(String name, int score) {
        this.name = name;
        this.score = score;
    }

    @Override
    public String toString() {
        return name + "(" + score + ")";
    }
}

class LeaderboardNode {

    int score;           // 分數作為鍵值
    Set<String> players; // 同分數的玩家列表
    int height;          // AVL 樹高度
    int subtreeSize;     // 子樹大小（用於排名計算）
    LeaderboardNode left, right;

    public LeaderboardNode(int score) {
        this.score = score;
        this.players = new HashSet<>();
        this.height = 1;
        this.subtreeSize = 0; // 初始沒有玩家
        this.left = this.right = null;
    }

    public int getBalance() {
        int leftHeight = (left != null) ? left.height : 0;
        int rightHeight = (right != null) ? right.height : 0;
        return leftHeight - rightHeight;
    }

    public void updateHeight() {
        int leftHeight = (left != null) ? left.height : 0;
        int rightHeight = (right != null) ? right.height : 0;
        this.height = Math.max(leftHeight, rightHeight) + 1;
    }

    public void updateSubtreeSize() {
        int leftSize = (left != null) ? left.subtreeSize : 0;
        int rightSize = (right != null) ? right.subtreeSize : 0;
        this.subtreeSize = leftSize + rightSize + players.size();
    }
}

public class AVLLeaderboardSystem {

    private LeaderboardNode root;
    private Map<String, Integer> playerScores; // 玩家名稱到分數的映射

    public AVLLeaderboardSystem() {
        this.root = null;
        this.playerScores = new HashMap<>();
    }

    // TODO: 添加玩家分數
    public void addPlayer(String playerName, int score) {
        // 提示：如果玩家已存在，先移除舊分數再添加新分數
        if (playerScores.containsKey(playerName)) {
            updatePlayerScore(playerName, score);
        } else {
            playerScores.put(playerName, score);
            root = insertScore(root, score, playerName);
        }
    }

    // TODO: 更新玩家分數
    public void updatePlayerScore(String playerName, int newScore) {
        // 提示：先移除玩家的舊分數，再添加新分數
        if (playerScores.containsKey(playerName)) {
            int oldScore = playerScores.get(playerName);
            root = removePlayerFromScore(root, oldScore, playerName);
            playerScores.put(playerName, newScore);
            root = insertScore(root, newScore, playerName);
        } else {
            addPlayer(playerName, newScore);
        }
    }

    // TODO: 查詢玩家排名（排名從1開始，分數越高排名越前）
    public int getPlayerRank(String playerName) {
        // 提示：需要計算比該玩家分數高的玩家數量
        if (!playerScores.containsKey(playerName)) {
            return -1; // 玩家不存在
        }

        int playerScore = playerScores.get(playerName);
        return getRankByScore(root, playerScore) + 1; // 排名從1開始
    }

    private int getRankByScore(LeaderboardNode node, int targetScore) {
        if (node == null) {
            return 0;
        }

        if (targetScore < node.score) {
            // 目標分數比當前節點小，排名在右子樹加上當前節點之後
            int rightSize = (node.right != null) ? node.right.subtreeSize : 0;
            return rightSize + node.players.size() + getRankByScore(node.left, targetScore);
        } else if (targetScore > node.score) {
            // 目標分數比當前節點大，排名在左子樹
            return getRankByScore(node.right, targetScore);
        } else {
            // 找到目標分數節點，排名是右子樹的大小
            return (node.right != null) ? node.right.subtreeSize : 0;
        }
    }

    // TODO: 查詢前 K 名玩家
    public List<Player> getTopKPlayers(int k) {
        // 提示：按分數從高到低遍歷，收集前k個玩家
        List<Player> topPlayers = new ArrayList<>();
        getTopKHelper(root, k, topPlayers);
        return topPlayers;
    }

    private void getTopKHelper(LeaderboardNode node, int k, List<Player> result) {
        if (node == null || result.size() >= k) {
            return;
        }

        // 先遍歷右子樹（高分數）
        getTopKHelper(node.right, k, result);

        // 添加當前節點的玩家
        if (result.size() < k) {
            for (String playerName : node.players) {
                if (result.size() < k) {
                    result.add(new Player(playerName, node.score));
                }
            }
        }

        // 再遍歷左子樹（低分數）
        getTopKHelper(node.left, k, result);
    }

    // TODO: 查詢指定排名的玩家（第k名的玩家）
    public List<Player> getPlayersAtRank(int rank) {
        // 提示：使用 select 操作找到第 rank 名的分數節點
        List<Player> players = new ArrayList<>();
        LeaderboardNode targetNode = selectByRank(root, rank);
        if (targetNode != null) {
            for (String playerName : targetNode.players) {
                players.add(new Player(playerName, targetNode.score));
            }
        }
        return players;
    }

    private LeaderboardNode selectByRank(LeaderboardNode node, int rank) {
        if (node == null) {
            return null;
        }

        int rightSize = (node.right != null) ? node.right.subtreeSize : 0;

        if (rank <= rightSize) {
            // 目標排名在右子樹
            return selectByRank(node.right, rank);
        } else if (rank <= rightSize + node.players.size()) {
            // 目標排名在當前節點
            return node;
        } else {
            // 目標排名在左子樹
            return selectByRank(node.left, rank - rightSize - node.players.size());
        }
    }

    // 插入分數節點
    private LeaderboardNode insertScore(LeaderboardNode node, int score, String playerName) {
        if (node == null) {
            LeaderboardNode newNode = new LeaderboardNode(score);
            newNode.players.add(playerName);
            newNode.updateSubtreeSize();
            return newNode;
        }

        if (score < node.score) {
            node.left = insertScore(node.left, score, playerName);
        } else if (score > node.score) {
            node.right = insertScore(node.right, score, playerName);
        } else {
            // 相同分數，加入玩家列表
            node.players.add(playerName);
            node.updateSubtreeSize();
            return node;
        }

        node.updateHeight();
        node.updateSubtreeSize();
        return balanceNode(node);
    }

    // 從分數節點移除玩家
    private LeaderboardNode removePlayerFromScore(LeaderboardNode node, int score, String playerName) {
        if (node == null) {
            return node;
        }

        if (score < node.score) {
            node.left = removePlayerFromScore(node.left, score, playerName);
        } else if (score > node.score) {
            node.right = removePlayerFromScore(node.right, score, playerName);
        } else {
            // 找到目標分數節點
            node.players.remove(playerName);

            // 如果該分數沒有玩家了，刪除節點
            if (node.players.isEmpty()) {
                if (node.left == null || node.right == null) {
                    LeaderboardNode temp = (node.left != null) ? node.left : node.right;
                    if (temp == null) {
                        node = null;
                    } else {
                        node = temp;
                    }
                } else {
                    // 有兩個子節點，找後繼節點
                    LeaderboardNode temp = findMin(node.right);
                    node.score = temp.score;
                    node.players = new HashSet<>(temp.players);
                    node.right = removePlayerFromScore(node.right, temp.score, temp.players.iterator().next());
                }
            }
        }

        if (node == null) {
            return node;
        }

        node.updateHeight();
        node.updateSubtreeSize();
        return balanceNode(node);
    }

    // 平衡操作
    private LeaderboardNode balanceNode(LeaderboardNode node) {
        int balance = node.getBalance();

        if (balance > 1 && node.left.getBalance() >= 0) {
            return rightRotate(node);
        }

        if (balance > 1 && node.left.getBalance() < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (balance < -1 && node.right.getBalance() <= 0) {
            return leftRotate(node);
        }

        if (balance < -1 && node.right.getBalance() > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    private LeaderboardNode rightRotate(LeaderboardNode y) {
        LeaderboardNode x = y.left;
        LeaderboardNode T2 = x.right;

        x.right = y;
        y.left = T2;

        y.updateHeight();
        y.updateSubtreeSize();
        x.updateHeight();
        x.updateSubtreeSize();

        return x;
    }

    private LeaderboardNode leftRotate(LeaderboardNode x) {
        LeaderboardNode y = x.right;
        LeaderboardNode T2 = y.left;

        y.left = x;
        x.right = T2;

        x.updateHeight();
        x.updateSubtreeSize();
        y.updateHeight();
        y.updateSubtreeSize();

        return y;
    }

    private LeaderboardNode findMin(LeaderboardNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    // 顯示排行榜
    public void printLeaderboard() {
        System.out.println("=== 排行榜 ===");
        List<Player> allPlayers = new ArrayList<>();
        collectAllPlayers(root, allPlayers);

        // 按分數降序排序
        allPlayers.sort((a, b) -> Integer.compare(b.score, a.score));

        for (int i = 0; i < allPlayers.size(); i++) {
            Player player = allPlayers.get(i);
            System.out.println((i + 1) + ". " + player);
        }
    }

    private void collectAllPlayers(LeaderboardNode node, List<Player> players) {
        if (node != null) {
            collectAllPlayers(node.right, players); // 先收集高分
            for (String playerName : node.players) {
                players.add(new Player(playerName, node.score));
            }
            collectAllPlayers(node.left, players); // 再收集低分
        }
    }

    public static void main(String[] args) {
        System.out.println("=== AVL 排行榜系統練習 ===");

        AVLLeaderboardSystem leaderboard = new AVLLeaderboardSystem();

        // 添加玩家
        System.out.println("\n--- 添加玩家 ---");
        String[] players = {"Alice", "Bob", "Charlie", "David", "Eve", "Frank", "Grace"};
        int[] scores = {1500, 1200, 1800, 1300, 1600, 1100, 1700};

        for (int i = 0; i < players.length; i++) {
            leaderboard.addPlayer(players[i], scores[i]);
            System.out.println("添加玩家: " + players[i] + " (分數: " + scores[i] + ")");
        }

        leaderboard.printLeaderboard();

        // 查詢排名
        System.out.println("\n--- 查詢玩家排名 ---");
        for (String player : players) {
            int rank = leaderboard.getPlayerRank(player);
            System.out.println(player + " 的排名: " + rank);
        }

        // 更新分數
        System.out.println("\n--- 更新分數 ---");
        leaderboard.updatePlayerScore("Bob", 1900);
        System.out.println("Bob 分數更新為 1900");
        leaderboard.updatePlayerScore("Eve", 1400);
        System.out.println("Eve 分數更新為 1400");

        leaderboard.printLeaderboard();

        // 查詢前K名
        System.out.println("\n--- 前3名玩家 ---");
        List<Player> top3 = leaderboard.getTopKPlayers(3);
        for (int i = 0; i < top3.size(); i++) {
            System.out.println((i + 1) + ". " + top3.get(i));
        }

        // 查詢指定排名
        System.out.println("\n--- 查詢第5名玩家 ---");
        List<Player> rank5Players = leaderboard.getPlayersAtRank(5);
        for (Player player : rank5Players) {
            System.out.println("第5名: " + player);
        }

        // 測試同分情況
        System.out.println("\n--- 測試同分情況 ---");
        leaderboard.addPlayer("Henry", 1600);
        leaderboard.addPlayer("Ivy", 1600);
        System.out.println("添加同分玩家 Henry 和 Ivy (分數: 1600)");

        leaderboard.printLeaderboard();

        System.out.println("Henry 的排名: " + leaderboard.getPlayerRank("Henry"));
        System.out.println("Ivy 的排名: " + leaderboard.getPlayerRank("Ivy"));

        System.out.println("\n練習提示：");
        System.out.println("1. 理解如何擴展 AVL 樹節點儲存額外資訊");
        System.out.println("2. 學習 select 和 rank 操作的實作");
        System.out.println("3. 處理同分玩家的情況");
        System.out.println("4. 分析各操作的時間複雜度");
    }
}

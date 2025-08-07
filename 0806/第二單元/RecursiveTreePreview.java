
import java.util.*;

public class RecursiveTreePreview {

    // ==================== 檔案系統模擬 ====================
    /**
     * 檔案系統節點類別
     */
    static class FileSystemNode {

        String name;
        boolean isDirectory;
        List<FileSystemNode> children;
        int fileSize; // 如果是檔案，表示檔案大小

        public FileSystemNode(String name, boolean isDirectory) {
            this.name = name;
            this.isDirectory = isDirectory;
            this.children = new ArrayList<>();
            this.fileSize = isDirectory ? 0 : (int) (Math.random() * 1000) + 1;
        }

        public void addChild(FileSystemNode child) {
            if (this.isDirectory) {
                this.children.add(child);
            }
        }
    }

    /**
     * 遞迴計算資料夾的總檔案數
     */
    public static int countTotalFiles(FileSystemNode node) {
        if (node == null) {
            return 0;
        }

        // 如果是檔案，回傳1
        if (!node.isDirectory) {
            return 1;
        }

        // 如果是資料夾，遞迴計算所有子節點的檔案數
        int totalFiles = 0;
        for (FileSystemNode child : node.children) {
            totalFiles += countTotalFiles(child);
        }

        return totalFiles;
    }

    /**
     * 遞迴計算資料夾的總大小
     */
    public static int calculateTotalSize(FileSystemNode node) {
        if (node == null) {
            return 0;
        }

        // 如果是檔案，回傳檔案大小
        if (!node.isDirectory) {
            return node.fileSize;
        }

        // 如果是資料夾，遞迴計算所有子節點的大小
        int totalSize = 0;
        for (FileSystemNode child : node.children) {
            totalSize += calculateTotalSize(child);
        }

        return totalSize;
    }

    /**
     * 遞迴顯示檔案系統結構
     */
    public static void printFileSystem(FileSystemNode node, String indent) {
        if (node == null) {
            return;
        }

        // 顯示當前節點
        String icon = node.isDirectory ? "📁" : "📄";
        String sizeInfo = node.isDirectory
                ? String.format("(%d files, %d KB)",
                        countTotalFiles(node),
                        calculateTotalSize(node))
                : String.format("(%d KB)", node.fileSize);

        System.out.printf("%s%s %s %s\n", indent, icon, node.name, sizeInfo);

        // 遞迴顯示子節點
        if (node.isDirectory) {
            for (FileSystemNode child : node.children) {
                printFileSystem(child, indent + "  ");
            }
        }
    }

    /**
     * 遞迴尋找特定檔案
     */
    public static FileSystemNode findFile(FileSystemNode node, String fileName) {
        if (node == null) {
            return null;
        }

        // 檢查當前節點
        if (node.name.equals(fileName)) {
            return node;
        }

        // 如果是資料夾，遞迴搜尋子節點
        if (node.isDirectory) {
            for (FileSystemNode child : node.children) {
                FileSystemNode found = findFile(child, fileName);
                if (found != null) {
                    return found;
                }
            }
        }

        return null;
    }

    // ==================== 多層選單結構 ====================
    /**
     * 選單項目類別
     */
    static class MenuItem {

        String title;
        String action;
        List<MenuItem> subMenus;
        int level;

        public MenuItem(String title, String action, int level) {
            this.title = title;
            this.action = action;
            this.level = level;
            this.subMenus = new ArrayList<>();
        }

        public void addSubMenu(MenuItem subMenu) {
            this.subMenus.add(subMenu);
        }
    }

    /**
     * 遞迴列印多層選單結構
     */
    public static void printMenu(MenuItem menu, String prefix) {
        if (menu == null) {
            return;
        }

        // 根據層級決定縮排和符號
        String indent = "  ".repeat(menu.level);
        String symbol = menu.level == 0 ? "■"
                : menu.level == 1 ? "▶"
                        : menu.level == 2 ? "▸" : "•";

        System.out.printf("%s%s%s %s\n", prefix, indent, symbol, menu.title);

        // 遞迴列印子選單
        for (MenuItem subMenu : menu.subMenus) {
            printMenu(subMenu, prefix);
        }
    }

    /**
     * 遞迴計算選單的總項目數
     */
    public static int countMenuItems(MenuItem menu) {
        if (menu == null) {
            return 0;
        }

        int count = 1; // 當前選單項目

        // 遞迴計算子選單項目
        for (MenuItem subMenu : menu.subMenus) {
            count += countMenuItems(subMenu);
        }

        return count;
    }

    /**
     * 遞迴尋找選單項目
     */
    public static MenuItem findMenuItem(MenuItem menu, String title) {
        if (menu == null) {
            return null;
        }

        // 檢查當前選單項目
        if (menu.title.equals(title)) {
            return menu;
        }

        // 遞迴搜尋子選單
        for (MenuItem subMenu : menu.subMenus) {
            MenuItem found = findMenuItem(subMenu, title);
            if (found != null) {
                return found;
            }
        }

        return null;
    }

    // ==================== 巢狀陣列展平 ====================
    /**
     * 遞迴處理巢狀陣列的展平 使用 Object 來處理不同類型的巢狀結構
     */
    public static List<Integer> flattenNestedArray(Object[] array) {
        List<Integer> result = new ArrayList<>();
        flattenHelper(array, result);
        return result;
    }

    private static void flattenHelper(Object element, List<Integer> result) {
        if (element == null) {
            return;
        }

        // 如果是整數，直接添加到結果
        if (element instanceof Integer) {
            result.add((Integer) element);
        } // 如果是陣列，遞迴處理
        else if (element instanceof Object[]) {
            Object[] array = (Object[]) element;
            for (Object item : array) {
                flattenHelper(item, result);
            }
        } // 如果是 List，遞迴處理
        else if (element instanceof List) {
            List<?> list = (List<?>) element;
            for (Object item : list) {
                flattenHelper(item, result);
            }
        }
    }

    /**
     * 遞迴處理字串表示的巢狀結構
     */
    public static List<String> flattenNestedStringStructure(String input) {
        List<String> result = new ArrayList<>();
        flattenStringHelper(input, 0, new StringBuilder(), result);
        return result;
    }

    private static int flattenStringHelper(String input, int index,
            StringBuilder current, List<String> result) {
        while (index < input.length()) {
            char ch = input.charAt(index);

            if (ch == '[') {
                // 開始新的巢狀層級
                index = flattenStringHelper(input, index + 1, new StringBuilder(), result);
            } else if (ch == ']') {
                // 結束當前層級
                if (current.length() > 0) {
                    result.add(current.toString().trim());
                }
                return index + 1;
            } else if (ch == ',') {
                // 分隔符，保存當前項目
                if (current.length() > 0) {
                    result.add(current.toString().trim());
                    current.setLength(0);
                }
                index++;
            } else {
                // 普通字符
                current.append(ch);
                index++;
            }
        }

        // 處理最後一個項目
        if (current.length() > 0) {
            result.add(current.toString().trim());
        }

        return index;
    }

    // ==================== 巢狀清單最大深度 ====================
    /**
     * 遞迴計算巢狀清單的最大深度
     */
    public static int calculateMaxDepth(Object element) {
        if (element == null) {
            return 0;
        }

        // 如果不是容器類型，深度為1
        if (!(element instanceof Object[]) && !(element instanceof List)) {
            return 1;
        }

        int maxDepth = 0;

        // 處理陣列
        if (element instanceof Object[]) {
            Object[] array = (Object[]) element;
            for (Object item : array) {
                maxDepth = Math.max(maxDepth, calculateMaxDepth(item));
            }
        } // 處理清單
        else if (element instanceof List) {
            List<?> list = (List<?>) element;
            for (Object item : list) {
                maxDepth = Math.max(maxDepth, calculateMaxDepth(item));
            }
        }

        return maxDepth + 1; // 當前層級 + 最大子層級深度
    }

    /**
     * 計算巢狀括號字串的最大深度
     */
    public static int calculateBracketDepth(String str) {
        return calculateBracketDepthHelper(str, 0, 0, 0);
    }

    private static int calculateBracketDepthHelper(String str, int index,
            int currentDepth, int maxDepth) {
        // 基底情況
        if (index >= str.length()) {
            return maxDepth;
        }

        char ch = str.charAt(index);

        if (ch == '[' || ch == '(' || ch == '{') {
            currentDepth++;
            maxDepth = Math.max(maxDepth, currentDepth);
        } else if (ch == ']' || ch == ')' || ch == '}') {
            currentDepth--;
        }

        // 遞迴處理下一個字符
        return calculateBracketDepthHelper(str, index + 1, currentDepth, maxDepth);
    }

    // ==================== 輔助方法 ====================
    /**
     * 建立示例檔案系統
     */
    public static FileSystemNode createSampleFileSystem() {
        FileSystemNode root = new FileSystemNode("root", true);

        // 建立 documents 資料夾
        FileSystemNode documents = new FileSystemNode("documents", true);
        documents.addChild(new FileSystemNode("report.pdf", false));
        documents.addChild(new FileSystemNode("presentation.pptx", false));

        // 建立 projects 資料夾
        FileSystemNode projects = new FileSystemNode("projects", true);
        FileSystemNode project1 = new FileSystemNode("project1", true);
        project1.addChild(new FileSystemNode("main.java", false));
        project1.addChild(new FileSystemNode("config.xml", false));
        projects.addChild(project1);

        FileSystemNode project2 = new FileSystemNode("project2", true);
        project2.addChild(new FileSystemNode("app.js", false));
        project2.addChild(new FileSystemNode("style.css", false));
        project2.addChild(new FileSystemNode("index.html", false));
        projects.addChild(project2);

        // 建立 media 資料夾
        FileSystemNode media = new FileSystemNode("media", true);
        FileSystemNode images = new FileSystemNode("images", true);
        images.addChild(new FileSystemNode("photo1.jpg", false));
        images.addChild(new FileSystemNode("photo2.png", false));
        media.addChild(images);

        FileSystemNode videos = new FileSystemNode("videos", true);
        videos.addChild(new FileSystemNode("movie.mp4", false));
        media.addChild(videos);

        // 組裝根目錄
        root.addChild(documents);
        root.addChild(projects);
        root.addChild(media);
        root.addChild(new FileSystemNode("readme.txt", false));

        return root;
    }

    /**
     * 建立示例選單系統
     */
    public static MenuItem createSampleMenu() {
        MenuItem mainMenu = new MenuItem("主選單", "main", 0);

        // 檔案選單
        MenuItem fileMenu = new MenuItem("檔案", "file", 1);
        fileMenu.addSubMenu(new MenuItem("開新檔案", "file.new", 2));
        fileMenu.addSubMenu(new MenuItem("開啟檔案", "file.open", 2));
        fileMenu.addSubMenu(new MenuItem("儲存檔案", "file.save", 2));
        MenuItem recent = new MenuItem("最近使用的檔案", "file.recent", 2);
        recent.addSubMenu(new MenuItem("檔案1.txt", "file.recent.1", 3));
        recent.addSubMenu(new MenuItem("檔案2.doc", "file.recent.2", 3));
        fileMenu.addSubMenu(recent);

        // 編輯選單
        MenuItem editMenu = new MenuItem("編輯", "edit", 1);
        editMenu.addSubMenu(new MenuItem("復原", "edit.undo", 2));
        editMenu.addSubMenu(new MenuItem("重做", "edit.redo", 2));
        editMenu.addSubMenu(new MenuItem("剪下", "edit.cut", 2));
        editMenu.addSubMenu(new MenuItem("複製", "edit.copy", 2));
        editMenu.addSubMenu(new MenuItem("貼上", "edit.paste", 2));

        // 檢視選單
        MenuItem viewMenu = new MenuItem("檢視", "view", 1);
        viewMenu.addSubMenu(new MenuItem("全螢幕", "view.fullscreen", 2));
        MenuItem zoom = new MenuItem("縮放", "view.zoom", 2);
        zoom.addSubMenu(new MenuItem("放大", "view.zoom.in", 3));
        zoom.addSubMenu(new MenuItem("縮小", "view.zoom.out", 3));
        zoom.addSubMenu(new MenuItem("重設", "view.zoom.reset", 3));
        viewMenu.addSubMenu(zoom);

        // 說明選單
        MenuItem helpMenu = new MenuItem("說明", "help", 1);
        helpMenu.addSubMenu(new MenuItem("使用手冊", "help.manual", 2));
        helpMenu.addSubMenu(new MenuItem("關於", "help.about", 2));

        mainMenu.addSubMenu(fileMenu);
        mainMenu.addSubMenu(editMenu);
        mainMenu.addSubMenu(viewMenu);
        mainMenu.addSubMenu(helpMenu);

        return mainMenu;
    }

    public static void main(String[] args) {
        System.out.println("=== 遞迴樹狀結構預習示範 ===");

        // 1. 檔案系統模擬
        System.out.println("1. 檔案系統結構：");
        FileSystemNode fileSystem = createSampleFileSystem();
        printFileSystem(fileSystem, "");

        System.out.printf("\n檔案系統統計：\n");
        System.out.printf("總檔案數：%d\n", countTotalFiles(fileSystem));
        System.out.printf("總大小：%d KB\n", calculateTotalSize(fileSystem));

        // 檔案搜尋測試
        String searchFile = "main.java";
        FileSystemNode found = findFile(fileSystem, searchFile);
        System.out.printf("搜尋檔案 \"%s\"：%s\n", searchFile,
                found != null ? "找到" : "未找到");

        System.out.println("\n" + "=".repeat(50));

        // 2. 多層選單結構
        System.out.println("2. 多層選單結構：");
        MenuItem menu = createSampleMenu();
        printMenu(menu, "");

        System.out.printf("\n選單統計：\n");
        System.out.printf("總選單項目數：%d\n", countMenuItems(menu));

        // 選單搜尋測試
        String searchMenuItem = "復原";
        MenuItem foundMenuItem = findMenuItem(menu, searchMenuItem);
        System.out.printf("搜尋選單項目 \"%s\"：%s\n", searchMenuItem,
                foundMenuItem != null ? "找到" : "未找到");

        System.out.println("\n" + "=".repeat(50));

        // 3. 巢狀陣列展平
        System.out.println("3. 巢狀陣列展平：");

        // 建立巢狀陣列
        Object[] nestedArray = {
            1,
            new Object[]{2, 3, new Object[]{4, 5}},
            6,
            new Object[]{7, new Object[]{8, 9, 10}}
        };

        List<Integer> flattened = flattenNestedArray(nestedArray);
        System.out.printf("原始巢狀結構：[1, [2, 3, [4, 5]], 6, [7, [8, 9, 10]]]\n");
        System.out.printf("展平後：%s\n", flattened);

        // 字串巢狀結構展平
        String nestedString = "[a,[b,c,[d,e]],f,[g,[h,i,j]]]";
        List<String> flattenedString = flattenNestedStringStructure(nestedString);
        System.out.printf("字串巢狀結構：%s\n", nestedString);
        System.out.printf("展平後：%s\n", flattenedString);

        System.out.println("\n" + "=".repeat(50));

        // 4. 巢狀清單最大深度
        System.out.println("4. 巢狀清單最大深度：");

        // 測試巢狀陣列深度
        Object[] deepArray = {
            1,
            new Object[]{
                2,
                new Object[]{
                    3,
                    new Object[]{4, 5}
                }
            }
        };

        int maxDepth = calculateMaxDepth(deepArray);
        System.out.printf("巢狀陣列：[1, [2, [3, [4, 5]]]]\n");
        System.out.printf("最大深度：%d\n", maxDepth);

        // 測試括號字串深度
        String[] bracketTests = {
            "((()))",
            "[a[b[c[d]]]]",
            "{[()]}",
            "((()))(())",
            "[[[]]]"
        };

        System.out.println("\n括號字串深度測試：");
        for (String test : bracketTests) {
            int depth = calculateBracketDepth(test);
            System.out.printf("\"%s\" -> 深度：%d\n", test, depth);
        }

        System.out.println("\n" + "=".repeat(50));

        // 5. 綜合示例
        System.out.println("5. 遞迴樹狀結構應用總結：");
        System.out.println("✓ 檔案系統遍歷 - 計算大小、檔案數量、搜尋檔案");
        System.out.println("✓ 選單系統管理 - 多層選單顯示、項目計數、項目搜尋");
        System.out.println("✓ 巢狀資料展平 - 多維陣列扁平化處理");
        System.out.println("✓ 深度計算 - 巢狀結構的層級分析");
        System.out.println("\n這些都是樹狀結構的基本應用，為後續的資料結構學習奠定基礎！");
    }
}

import java.util.*;

public class M02_YouBikeNextArrival {
    
    public static int timeToMinutes(String time) {
        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        return hours * 60 + minutes;
    }
    
    public static String minutesToTime(int minutes) {
        int hours = minutes / 60;
        int mins = minutes % 60;
        return String.format("%02d:%02d", hours, mins);
    }
    
    public static int binarySearchNext(int[] times, int query) {
        int left = 0;
        int right = times.length - 1;
        int result = -1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (times[mid] > query) {
                result = times[mid];
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        
        return result;
    }
    
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();
            scanner.nextLine();
            
            int[] times = new int[n];
            for (int i = 0; i < n; i++) {
                String timeStr = scanner.nextLine();
                times[i] = timeToMinutes(timeStr);
            }
            
            String queryStr = scanner.nextLine();
            int queryTime = timeToMinutes(queryStr);
            
            int nextTime = binarySearchNext(times, queryTime);
            
            if (nextTime == -1) {
                System.out.println("No bike");
            } else {
                System.out.println(minutesToTime(nextTime));
            }
        }
    }
}

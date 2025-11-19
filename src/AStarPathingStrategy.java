import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

class AStarPathingStrategy implements PathingStrategy {


    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors)
    {
        List<Point> path = new LinkedList<>();

        // Open list -- sorts based on level first, then by its f cost by ascending order
        PriorityQueue<Point> openList = new PriorityQueue<>(
                Comparator.comparing(Point::getfCost)
                        .thenComparing(Point::getgCost));

        // Close list -- set of unique points to mark whats visited
        Set<Point> closeList = new HashSet<>();
        Map<Point, Point> parentMap = new HashMap<>();

        // set the current Point to the start to begin
        Point currPoint = start;
        currPoint.gCost = 0;
        currPoint.fCost = computeDist(start, end);
        openList.add(currPoint);
        parentMap.put(currPoint, null);

        double hCost;
        while(!(openList.isEmpty())) {
            //find compatible neighbors near the currPoint
            currPoint = openList.poll();
            // Skip if already visited (handles duplicates in queue)
            if (closeList.contains(currPoint)) continue;
            closeList.add(currPoint);

            if(withinReach.test(currPoint, end)) {
                return computePath(parentMap, currPoint);
            }

            // get neighbors
            //filter only the points can be passed through
            List<Point> neighbors = potentialNeighbors.apply(currPoint).filter(canPassThrough).toList();


            // increment it level by level
            for (Point neighbor: neighbors) {
                // if exists already dont perform computations
                if(openList.contains(neighbor) || closeList.contains(neighbor)) continue;

                // compute the costs for g, h, and f
                neighbor.gCost = currPoint.gCost + 1;
                hCost = computeDist(neighbor, end);
                neighbor.fCost = neighbor.gCost + hCost;

                // set the parent of the neighbor to the current point
                parentMap.put(neighbor,currPoint);
                openList.add(neighbor);
            }
        }
        /*define closed list
          define open list
          while (true){
            Filtered list containing neighbors you can actually move to
            Check if any of the neighbors are beside the target
            set the g, h, f values
            add them to open list if not in open list
            add the selected node to close list
          return path*/
         return path;
    }

    // Computes dist from neighbor point
    public double computeDist(Point start, Point end) {
        return Math.abs(end.x - start.x) + Math.abs(end.y - start.y);
    }

    public List<Point> computePath(Map<Point, Point> mp, Point endNode) {
        List<Point> res = new ArrayList<>();

        Point temp = endNode;

        while(temp != null) {
            res.add(temp);
            temp = mp.get(temp);
        }
        // Remove start node if you don't want it in the path
        if (!res.isEmpty()) res.removeLast();  // Remove last element (which is start)
        return res.reversed();
    }
}

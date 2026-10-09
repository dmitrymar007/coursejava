package task11;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        BrokenEquality.Point p = new BrokenEquality.Point(1, 2);
        BrokenEquality.ColoredPoint cp = new BrokenEquality.ColoredPoint(1, 2, "red");
        System.out.println(p.equals(cp));
        System.out.println(cp.equals(p));

        List<BrokenEquality.Point> points = new ArrayList<>(List.of(p));
        List<BrokenEquality.Point> colored = new ArrayList<>(List.of(cp));
        System.out.println(points.contains(cp));
        System.out.println(colored.contains(p));

        StrictEquality.Point sp = new StrictEquality.Point(1, 2);
        StrictEquality.ColoredPoint scp = new StrictEquality.ColoredPoint(1, 2, "red");
        System.out.println(sp.equals(scp));
        System.out.println(scp.equals(sp));

        StrictEquality.Point anonymous = new StrictEquality.Point(1, 2) { };
        System.out.println(sp.equals(anonymous));

        CompositionEquality.ColoredPoint c1 = new CompositionEquality.ColoredPoint(1, 2, "red");
        CompositionEquality.ColoredPoint c2 = new CompositionEquality.ColoredPoint(1, 2, "red");
        CompositionEquality.Point plain = c1.point();
        System.out.println(c1.equals(c2));
        System.out.println(c1.equals(plain));
        System.out.println(plain.equals(c1));
        System.out.println(plain.equals(new CompositionEquality.Point(1, 2)));
    }
}

public class Body {
  public double xxPos;
  public double yyPos;
  public double xxVel;
  public double yyVel;
  public double mass;
  public String imgFileName;
  public static final double G = 6.67e-11;

  public Body(double xP, double yP, double xV, double yV, double m, String img) {
    xxPos = xP;
    yyPos = yP;
    xxVel = xV;
    yyVel = yV;
    mass = m;
    imgFileName = img;
  }

  public Body(Body b) {
    xxPos = b.xxPos;
    yyPos = b.yyPos;
    xxVel = b.xxVel;
    yyVel = b.yyVel;
    mass = b.mass;
    imgFileName = b.imgFileName;
  }

  public double calcDistance(Body b) {
    return Math.sqrt(Math.pow(b.xxPos-xxPos,2) + Math.pow(b.yyPos-yyPos,2));
  }

  public double calcForceExertedBy(Body second) {
    double dist = this.calcDistance(second);
    double force = G*mass*second.mass/(dist*dist);
    return force;
  }

  public double calcForceExertedByX(Body second) {
    double force = this.calcForceExertedBy(second);
    double dist = this.calcDistance(second);
    double forcex = force*(second.xxPos-xxPos)/dist;
    return forcex;
  }

  public double calcForceExertedByY(Body second) {
    double force = this.calcForceExertedBy(second);
    double dist = this.calcDistance(second);
    double forcey = force*(second.yyPos-yyPos)/dist;
    return forcey;
  }

  public double calcNetForceExertedByX(Body[] allBodys) {
    double netforcex = 0;
    for (int i = 0; i < allBodys.length; i++) {
      if (this.equals(allBodys[i])) {
        continue;
      }
      netforcex = netforcex + this.calcForceExertedByX(allBodys[i]);
    }
    return netforcex;
  }

  public double calcNetForceExertedByY(Body[] allBodys) {
    double netforcey = 0;
    for (int i = 0; i < allBodys.length; i++) {
      if (this.equals(allBodys[i])) {
        continue;
      }
      netforcey = netforcey + this.calcForceExertedByY(allBodys[i]);
    }
    return netforcey;
  }

  public void update(double dt, double forcex, double forcey) {
    double accx = forcex/mass;
    double accy = forcey/mass;
    xxVel = xxVel + dt*accx;
    yyVel = yyVel + dt*accy;
    xxPos = xxPos + dt*xxVel;
    yyPos = yyPos + dt*yyVel;
  }

  public void draw() {
    String imgPlanet = "images/"+imgFileName;
    StdDraw.picture(xxPos, yyPos, imgPlanet);
  }
  
}

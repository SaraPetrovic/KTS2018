package ftn.kts.transport.model;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "KTS_ROUTE")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Line line;

    @Column(unique = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    private Vehicle vehicle;

    @Column
    private boolean active;

    public Route(){

    }

    public Route(Line line, Date date, Vehicle vehicle, boolean active) {
        this.line = line;
        this.date = date;
        this.vehicle = vehicle;
        this.active = active;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Route(Line line, Date date, Vehicle vehicle) {
        this.line = line;
        this.date = date;
        this.vehicle = vehicle;
    }

    public Route(Long id, Line line, Date date) {
        this.id = id;
        this.line = line;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

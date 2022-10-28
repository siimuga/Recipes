package recipes;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "RECIPES")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @NotBlank
    @Column(name = "NAME")
    private String name;
    @NotBlank
    @Column(name = "DESCRIPTION")
    private String description;
    @NotBlank
    @Column(name = "CATEGORY")
    private String category;
    @Column(name = "datex", nullable = false)
    private Instant date;
    @NotEmpty
    @ElementCollection
    @JoinColumn(name = "INGREDIENTS_id", nullable = false)
    @Column(name = "INGREDIENTS")
    private List<String> ingredients= new ArrayList<>();
    @NotEmpty
    @ElementCollection
    @JoinColumn(name = "DIRECTIONS_id", nullable = false)
    @Column(name = "DIRECTIONS")
    private List<String> directions = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}

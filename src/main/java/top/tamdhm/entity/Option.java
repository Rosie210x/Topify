package top.tamdhm.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "options")
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id")
    private Integer optionId;

    @Column(name = "label", nullable = false, length = 50)
    private String label;

    @Column(name = "content", nullable = false, length = 1000)
    private String content;

    @Column(name = "image_url", length = 1000)
    private String imageUrl;

    @Column(name = "metadata", length = 2000)
    private String metadata;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "war_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private War war;

    @OneToMany(mappedBy = "option", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Vote> votes = new ArrayList<>();
}
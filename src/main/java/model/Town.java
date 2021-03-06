package model;

import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.FetchProfile;
import org.hibernate.annotations.NaturalId;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Audited
@FetchProfile(
        name = "town.complete",
        fetchOverrides = {
                @FetchProfile.FetchOverride(
                        entity = Town.class,
                        association = "inhabitants",
                        mode = FetchMode.JOIN
                )
        }
)
@NamedEntityGraph(
        name = "graph.town.complete",
        attributeNodes = {
                @NamedAttributeNode(value = "inhabitants", subgraph = "companies")
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "companies",
                        attributeNodes = @NamedAttributeNode("companies")
                )
        }
)
public class Town extends PersistableObject {

    @NaturalId
    String name;

    @OneToMany(mappedBy = "town")
    List<Person> inhabitants = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Person> getInhabitants() {
        return inhabitants;
    }
}

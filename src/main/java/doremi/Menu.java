package doremi;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="Menu_table")
public class Menu {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String menuName;
    private String menuType;
    private String description;
    private Float price;

    @PostPersist
    public void onPostPersist(){
        MenuRegistered menuRegistered = new MenuRegistered();
        BeanUtils.copyProperties(this, menuRegistered);
        menuRegistered.publishAfterCommit();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        Date date = new Date();
        Date regTime = new Timestamp(date.getTime());

        doremi.external.MenuScore menuScore = new doremi.external.MenuScore();
        // mappings goes here
        menuScore.setMenuId(menuRegistered.getId());
        menuScore.setStatus("saved");
        menuScore.setScore(0);
        System.out.println("##### MenuScore onPostPersist : " + menuScore.getMenuId()+"<<<<<<<");
        AdminApplication.applicationContext.getBean(doremi.external.MenuScoreService.class)
                .saveRequest(menuScore);
    }

    @PostRemove
    public void onPostRemove(){
        System.out.println("##### MenuScore onPostRemove <<<<<<<<<");
        MenuDeleted menuDeleted = new MenuDeleted();
        BeanUtils.copyProperties(this, menuDeleted);
        menuDeleted.publishAfterCommit();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }




}

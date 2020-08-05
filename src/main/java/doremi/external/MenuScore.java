package doremi.external;

import java.util.Date;

public class MenuScore {

    private Long id;
    private Long menuId;
    private Integer score;
    private String status;
    private Date chgDate;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getMenuId() {
        return menuId;
    }
    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }
    public Integer getScore() {
        return score;
    }
    public void setScore(Integer score) {
        this.score = score;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Date getChgDate() {
        return chgDate;
    }
    public void setChgDate(Date chgDate) {
        this.chgDate = chgDate;
    }

}

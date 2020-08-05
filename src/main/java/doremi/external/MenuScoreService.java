
package doremi.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@FeignClient(name="menuScore", url="${api.url.point}")
//@FeignClient(name="menuScore", url="http://localhost:8085")
public interface MenuScoreService {

    @RequestMapping(method= RequestMethod.POST, path="/menuscore/save")
    public void saveRequest(@RequestBody MenuScore menuScore);

}
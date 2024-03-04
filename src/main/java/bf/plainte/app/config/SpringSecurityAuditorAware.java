/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.plainte.app.config;

import bf.plainte.app.security.SecurityUtils;
import bf.plainte.app.utils.Constants;
import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(SecurityUtils.getCurrentUserUsername().orElse(Constants.SYSTEM_ACCOUNT));
    }
}

package cn.wazitang.gateway.repo;

import cn.wazitang.gateway.domain.CustomRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface CustomRouteRepo extends JpaRepository<CustomRoute, Long> {
}

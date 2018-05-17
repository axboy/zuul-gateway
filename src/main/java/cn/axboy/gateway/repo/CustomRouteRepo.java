package cn.axboy.gateway.repo;

import cn.axboy.gateway.domain.CustomRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface CustomRouteRepo extends JpaRepository<CustomRoute, Long> {
}

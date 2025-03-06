package org.hankki.hankkiserver.domain.store.repository;

import static org.hankki.hankkiserver.domain.store.model.QStore.store;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.hankki.hankkiserver.api.store.parameter.SortOption;
import org.springframework.stereotype.Component;

@Component
public class OrderSpecifierProvider {
    public OrderSpecifier[] createOrderSpecifier(SortOption sortOption) {

        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();
        if (Objects.isNull(sortOption)){
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, store.id));
        }
        else if (SortOption.LATEST == sortOption){
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, store.id));
        }
        else if (SortOption.LOWESTPRICE == sortOption){
            orderSpecifiers.add(new OrderSpecifier(Order.ASC, store.lowestPrice));
        }
        else if (SortOption.RECOMMENDED == sortOption){
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, store.heartCount));
        }
        return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
    }

    public OrderSpecifier[] createOrderSpecifierForPaging(SortOption sortOption) {
        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();
        if (Objects.isNull(sortOption)){
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, store.id));
        }
        else if (SortOption.LATEST == sortOption){
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, store.id));
        }
        else if (SortOption.LOWESTPRICE == sortOption){
            orderSpecifiers.add(new OrderSpecifier(Order.ASC, store.lowestPrice));
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, store.id));
        }
        else if (SortOption.RECOMMENDED == sortOption){
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, store.heartCount));
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, store.id));
        }
        return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
    }
}

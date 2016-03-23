package krabiv.ro.ua.kordonq.lodainfo;

import java.util.List;

public interface AfterRequest {

    void call(List<Q> queues);
}

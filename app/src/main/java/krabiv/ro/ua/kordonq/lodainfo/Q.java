package krabiv.ro.ua.kordonq.lodainfo;

public class Q {

    int viewId;
    String qSize;

    public Q(final int viewId, final String qSize) {
        this.viewId = viewId;
        this.qSize = qSize;
    }

    public int getViewId() {
        return viewId;
    }

    public String getQSize() {
        return qSize;
    }
}

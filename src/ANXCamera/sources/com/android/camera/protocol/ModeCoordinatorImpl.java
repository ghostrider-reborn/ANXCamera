package com.android.camera.protocol;

import android.support.annotation.Nullable;
import com.android.camera.protocol.ModeProtocol;
import java.util.concurrent.ConcurrentHashMap;

public class ModeCoordinatorImpl implements ModeProtocol.ModeCoordinator {
    private static ModeCoordinatorImpl sInstance;
    private int mHolderKey;
    private ConcurrentHashMap<Integer, ModeProtocol.BaseProtocol> protocolMap = new ConcurrentHashMap<>();

    public static void create(int i) {
        getInstance();
        sInstance.mHolderKey = i;
    }

    @Deprecated
    public static void destroyAll(int i) {
        if (sInstance != null && sInstance.mHolderKey == i) {
            sInstance.destroyWorkspace();
            sInstance = null;
        }
    }

    private void destroyWorkspace() {
        this.protocolMap.clear();
    }

    public static void forceDestroy() {
        if (sInstance != null) {
            sInstance.destroyWorkspace();
            sInstance = null;
        }
    }

    public static ModeCoordinatorImpl getInstance() {
        if (sInstance == null) {
            synchronized (ModeCoordinatorImpl.class) {
                if (sInstance == null) {
                    sInstance = new ModeCoordinatorImpl();
                }
            }
        }
        return sInstance;
    }

    public static boolean isAlive(int i) {
        return sInstance != null && sInstance.mHolderKey == i;
    }

    private <P extends ModeProtocol.BaseProtocol> P nullCatcher(Class<P> cls) {
        try {
            return (ModeProtocol.BaseProtocol) cls.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        }
        return null;
    }

    public <P extends ModeProtocol.BaseProtocol> void attachProtocol(int i, @Nullable P p) {
        this.protocolMap.put(Integer.valueOf(i), p);
    }

    public <P extends ModeProtocol.BaseProtocol> void detachProtocol(int i, P p) {
        if (getAttachProtocol(i) == p) {
            this.protocolMap.remove(Integer.valueOf(i));
        }
    }

    public int getActiveProtocolSize() {
        return this.protocolMap.size();
    }

    public <P extends ModeProtocol.BaseProtocol> P getAttachProtocol(int i) {
        return (ModeProtocol.BaseProtocol) this.protocolMap.get(Integer.valueOf(i));
    }
}

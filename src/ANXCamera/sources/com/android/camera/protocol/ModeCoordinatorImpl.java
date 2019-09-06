package com.android.camera.protocol;

import android.support.annotation.Nullable;
import com.android.camera.protocol.ModeProtocol.BaseProtocol;
import com.android.camera.protocol.ModeProtocol.ModeCoordinator;
import java.util.concurrent.ConcurrentHashMap;

public class ModeCoordinatorImpl implements ModeCoordinator {
    private static ModeCoordinatorImpl sInstance;
    private int mHolderKey;
    private ConcurrentHashMap<Integer, BaseProtocol> protocolMap = new ConcurrentHashMap<>();

    public static void create(int i) {
        getInstance();
        sInstance.mHolderKey = i;
    }

    @Deprecated
    public static void destroyAll(int i) {
        ModeCoordinatorImpl modeCoordinatorImpl = sInstance;
        if (modeCoordinatorImpl != null && modeCoordinatorImpl.mHolderKey == i) {
            modeCoordinatorImpl.destroyWorkspace();
            sInstance = null;
        }
    }

    private void destroyWorkspace() {
        this.protocolMap.clear();
    }

    public static void forceDestroy() {
        ModeCoordinatorImpl modeCoordinatorImpl = sInstance;
        if (modeCoordinatorImpl != null) {
            modeCoordinatorImpl.destroyWorkspace();
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
        ModeCoordinatorImpl modeCoordinatorImpl = sInstance;
        return modeCoordinatorImpl != null && modeCoordinatorImpl.mHolderKey == i;
    }

    private <P extends BaseProtocol> P nullCatcher(Class<P> cls) {
        try {
            return (BaseProtocol) cls.newInstance();
        } catch (InstantiationException e2) {
            e2.printStackTrace();
        } catch (IllegalAccessException e3) {
            e3.printStackTrace();
        }
        return null;
    }

    public <P extends BaseProtocol> void attachProtocol(int i, @Nullable P p) {
        this.protocolMap.put(Integer.valueOf(i), p);
    }

    public <P extends BaseProtocol> void detachProtocol(int i, P p) {
        if (getAttachProtocol(i) == p) {
            this.protocolMap.remove(Integer.valueOf(i));
        }
    }

    public int getActiveProtocolSize() {
        return this.protocolMap.size();
    }

    public <P extends BaseProtocol> P getAttachProtocol(int i) {
        return (BaseProtocol) this.protocolMap.get(Integer.valueOf(i));
    }
}

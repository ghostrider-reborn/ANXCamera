package com.android.internal.app;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.statistics.E2EScenario;
import android.os.statistics.E2EScenarioPayload;
import android.os.statistics.E2EScenarioSettings;
import java.util.List;
import java.util.Map;

public interface IPerfShielder extends IInterface {

    public static abstract class Stub extends Binder implements IPerfShielder {
        private static final String DESCRIPTOR = "com.android.internal.app.IPerfShielder";
        static final int TRANSACTION_abortMatchingScenario = 30;
        static final int TRANSACTION_abortSpecificScenario = 31;
        static final int TRANSACTION_addActivityLaunchTime = 2;
        static final int TRANSACTION_addCallingPkgHookRule = 26;
        static final int TRANSACTION_addTimeConsumingIntent = 16;
        static final int TRANSACTION_beginScenario = 29;
        static final int TRANSACTION_clearTimeConsumingIntent = 18;
        static final int TRANSACTION_closeCheckPriority = 13;
        static final int TRANSACTION_deleteFilterInfo = 37;
        static final int TRANSACTION_deletePackageInfo = 23;
        static final int TRANSACTION_deleteRedirectRule = 21;
        static final int TRANSACTION_finishMatchingScenario = 32;
        static final int TRANSACTION_finishSpecificScenario = 33;
        static final int TRANSACTION_getAllRunningProcessMemInfos = 7;
        static final int TRANSACTION_getFreeMemory = 24;
        static final int TRANSACTION_getMemoryTrimLevel = 19;
        static final int TRANSACTION_getPackageNameByPid = 5;
        static final int TRANSACTION_getPerfEventSocketFd = 28;
        static final int TRANSACTION_insertFilterInfo = 36;
        static final int TRANSACTION_insertPackageInfo = 22;
        static final int TRANSACTION_insertRedirectRule = 20;
        static final int TRANSACTION_killUnusedApp = 4;
        static final int TRANSACTION_removeCallingPkgHookRule = 27;
        static final int TRANSACTION_removeServicePriority = 12;
        static final int TRANSACTION_removeTimeConsumingIntent = 17;
        static final int TRANSACTION_reportAnr = 25;
        static final int TRANSACTION_reportExcessiveCpuUsageRecords = 34;
        static final int TRANSACTION_reportNotificationClick = 35;
        static final int TRANSACTION_reportPerceptibleJank = 1;
        static final int TRANSACTION_resolveQuickAppInfos = 38;
        static final int TRANSACTION_setForkedProcessGroup = 6;
        static final int TRANSACTION_setHapLinks = 39;
        static final int TRANSACTION_setMiuiBroadcastDispatchEnable = 15;
        static final int TRANSACTION_setMiuiContentProviderControl = 14;
        static final int TRANSACTION_setSchedFgPid = 3;
        static final int TRANSACTION_setServicePriority = 10;
        static final int TRANSACTION_setServicePriorityWithNoProc = 11;
        static final int TRANSACTION_updateProcessFullMemInfoByPids = 8;
        static final int TRANSACTION_updateProcessPartialMemInfoByPids = 9;

        private static class Proxy implements IPerfShielder {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            public void reportPerceptibleJank(int callingPid, int renderThreadTid, String windowName, long totalDuration, long maxFrameDuration, long endTs, int selfCause, long num_frames) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(callingPid);
                    try {
                        _data.writeInt(renderThreadTid);
                        try {
                            _data.writeString(windowName);
                            try {
                                _data.writeLong(totalDuration);
                            } catch (Throwable th) {
                                th = th;
                                long j = maxFrameDuration;
                                long j2 = endTs;
                                int i = selfCause;
                                long j3 = num_frames;
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            long j4 = totalDuration;
                            long j5 = maxFrameDuration;
                            long j22 = endTs;
                            int i2 = selfCause;
                            long j32 = num_frames;
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        String str = windowName;
                        long j42 = totalDuration;
                        long j52 = maxFrameDuration;
                        long j222 = endTs;
                        int i22 = selfCause;
                        long j322 = num_frames;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeLong(maxFrameDuration);
                        try {
                            _data.writeLong(endTs);
                            try {
                                _data.writeInt(selfCause);
                                try {
                                    _data.writeLong(num_frames);
                                } catch (Throwable th4) {
                                    th = th4;
                                    _data.recycle();
                                    throw th;
                                }
                            } catch (Throwable th5) {
                                th = th5;
                                long j3222 = num_frames;
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th6) {
                            th = th6;
                            int i222 = selfCause;
                            long j32222 = num_frames;
                            _data.recycle();
                            throw th;
                        }
                        try {
                            this.mRemote.transact(1, _data, (Parcel) null, 1);
                            _data.recycle();
                        } catch (Throwable th7) {
                            th = th7;
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th8) {
                        th = th8;
                        long j2222 = endTs;
                        int i2222 = selfCause;
                        long j322222 = num_frames;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th9) {
                    th = th9;
                    int i3 = renderThreadTid;
                    String str2 = windowName;
                    long j422 = totalDuration;
                    long j522 = maxFrameDuration;
                    long j22222 = endTs;
                    int i22222 = selfCause;
                    long j3222222 = num_frames;
                    _data.recycle();
                    throw th;
                }
            }

            public void addActivityLaunchTime(String packageName, String activityName, long launchStartTime, long launchEndTime, boolean fromHome, boolean isColdStart) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(activityName);
                    _data.writeLong(launchStartTime);
                    _data.writeLong(launchEndTime);
                    _data.writeInt(fromHome);
                    _data.writeInt(isColdStart);
                    this.mRemote.transact(2, _data, (Parcel) null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void setSchedFgPid(int pid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(pid);
                    this.mRemote.transact(3, _data, (Parcel) null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void killUnusedApp(int uid, int pid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeInt(pid);
                    this.mRemote.transact(4, _data, (Parcel) null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public String getPackageNameByPid(int pid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(pid);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    return _reply.readString();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setForkedProcessGroup(int puid, int ppid, int group, String processName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(puid);
                    _data.writeInt(ppid);
                    _data.writeInt(group);
                    _data.writeString(processName);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<Bundle> getAllRunningProcessMemInfos() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    return _reply.createTypedArrayList(Bundle.CREATOR);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<Bundle> updateProcessFullMemInfoByPids(int[] pids) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeIntArray(pids);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    return _reply.createTypedArrayList(Bundle.CREATOR);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<Bundle> updateProcessPartialMemInfoByPids(int[] pids) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeIntArray(pids);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    return _reply.createTypedArrayList(Bundle.CREATOR);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setServicePriority(List<MiuiServicePriority> servicePrioritys) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(servicePrioritys);
                    this.mRemote.transact(10, _data, (Parcel) null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void setServicePriorityWithNoProc(List<MiuiServicePriority> servicePrioritys, long noProcDelayTime) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(servicePrioritys);
                    _data.writeLong(noProcDelayTime);
                    this.mRemote.transact(11, _data, (Parcel) null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void removeServicePriority(MiuiServicePriority servicePriority, boolean inBlacklist) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (servicePriority != null) {
                        _data.writeInt(1);
                        servicePriority.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(inBlacklist);
                    this.mRemote.transact(12, _data, (Parcel) null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void closeCheckPriority() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setMiuiContentProviderControl(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(enable);
                    this.mRemote.transact(14, _data, (Parcel) null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void setMiuiBroadcastDispatchEnable(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(enable);
                    this.mRemote.transact(15, _data, (Parcel) null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void addTimeConsumingIntent(String[] actions) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringArray(actions);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeTimeConsumingIntent(String[] actions) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringArray(actions);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void clearTimeConsumingIntent() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getMemoryTrimLevel() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                    return _reply.readInt();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean insertRedirectRule(String callingPkg, String destPkg, String redirectPkgname, Bundle clsNameMap) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPkg);
                    _data.writeString(destPkg);
                    _data.writeString(redirectPkgname);
                    boolean _result = true;
                    if (clsNameMap != null) {
                        _data.writeInt(1);
                        clsNameMap.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean deleteRedirectRule(String callingPkg, String destPkg) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPkg);
                    _data.writeString(destPkg);
                    boolean _result = false;
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean insertPackageInfo(PackageInfo pInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (pInfo != null) {
                        _data.writeInt(1);
                        pInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean deletePackageInfo(String pkgName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkgName);
                    boolean _result = false;
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long getFreeMemory() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                    return _reply.readLong();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void reportAnr(int callingPid, String windowName, long totalDuration, long endTs, String cpuInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(callingPid);
                    _data.writeString(windowName);
                    _data.writeLong(totalDuration);
                    _data.writeLong(endTs);
                    _data.writeString(cpuInfo);
                    this.mRemote.transact(25, _data, (Parcel) null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public boolean addCallingPkgHookRule(String hostApp, String originCallingPkg, String hookCallingPkg) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(hostApp);
                    _data.writeString(originCallingPkg);
                    _data.writeString(hookCallingPkg);
                    boolean _result = false;
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean removeCallingPkgHookRule(String hostApp, String originCallingPkg) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(hostApp);
                    _data.writeString(originCallingPkg);
                    boolean _result = false;
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ParcelFileDescriptor getPerfEventSocketFd() throws RemoteException {
                ParcelFileDescriptor _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = (ParcelFileDescriptor) ParcelFileDescriptor.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Bundle beginScenario(E2EScenario scenario, E2EScenarioSettings settings, String tag, E2EScenarioPayload payload, int tid, long uptimeMillis, boolean needResultBundle) throws RemoteException {
                Bundle _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (scenario != null) {
                        _data.writeInt(1);
                        scenario.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (settings != null) {
                        _data.writeInt(1);
                        settings.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(tag);
                    if (payload != null) {
                        _data.writeInt(1);
                        payload.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(tid);
                    _data.writeLong(uptimeMillis);
                    _data.writeInt(needResultBundle);
                    this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = (Bundle) Bundle.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void abortMatchingScenario(E2EScenario scenario, String tag, int tid, long uptimeMillis) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (scenario != null) {
                        _data.writeInt(1);
                        scenario.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(tag);
                    _data.writeInt(tid);
                    _data.writeLong(uptimeMillis);
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void abortSpecificScenario(Bundle scenarioBundle, int tid, long uptimeMillis) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (scenarioBundle != null) {
                        _data.writeInt(1);
                        scenarioBundle.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(tid);
                    _data.writeLong(uptimeMillis);
                    this.mRemote.transact(31, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void finishMatchingScenario(E2EScenario scenario, String tag, E2EScenarioPayload payload, int tid, long uptimeMillis) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (scenario != null) {
                        _data.writeInt(1);
                        scenario.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(tag);
                    if (payload != null) {
                        _data.writeInt(1);
                        payload.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(tid);
                    _data.writeLong(uptimeMillis);
                    this.mRemote.transact(32, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void finishSpecificScenario(Bundle scenarioBundle, E2EScenarioPayload payload, int tid, long uptimeMillis) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (scenarioBundle != null) {
                        _data.writeInt(1);
                        scenarioBundle.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (payload != null) {
                        _data.writeInt(1);
                        payload.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(tid);
                    _data.writeLong(uptimeMillis);
                    this.mRemote.transact(33, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void reportExcessiveCpuUsageRecords(List<Bundle> records) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(records);
                    this.mRemote.transact(34, _data, (Parcel) null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public void reportNotificationClick(String postPackage, Intent intent, long uptimeMillis) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(postPackage);
                    if (intent != null) {
                        _data.writeInt(1);
                        intent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeLong(uptimeMillis);
                    this.mRemote.transact(35, _data, (Parcel) null, 1);
                } finally {
                    _data.recycle();
                }
            }

            public boolean insertFilterInfo(String packageName, String name, Uri iconUri, List<Bundle> filterInfos) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(name);
                    boolean _result = true;
                    if (iconUri != null) {
                        _data.writeInt(1);
                        iconUri.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeTypedList(filterInfos);
                    this.mRemote.transact(36, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean deleteFilterInfo(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    boolean _result = false;
                    this.mRemote.transact(37, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<QuickAppResolveInfo> resolveQuickAppInfos(Intent targetIntent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (targetIntent != null) {
                        _data.writeInt(1);
                        targetIntent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(38, _data, _reply, 0);
                    _reply.readException();
                    return _reply.createTypedArrayList(QuickAppResolveInfo.CREATOR);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setHapLinks(Map data, ActivityInfo activityInfo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeMap(data);
                    if (activityInfo != null) {
                        _data.writeInt(1);
                        activityInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(39, _data, (Parcel) null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IPerfShielder asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IPerfShielder)) {
                return new Proxy(obj);
            }
            return (IPerfShielder) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        /*  JADX ERROR: NullPointerException in pass: CodeShrinkVisitor
            java.lang.NullPointerException
            	at jadx.core.dex.instructions.args.InsnArg.wrapInstruction(InsnArg.java:117)
            	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.inline(CodeShrinkVisitor.java:119)
            	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.shrinkBlock(CodeShrinkVisitor.java:70)
            	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.shrinkMethod(CodeShrinkVisitor.java:42)
            	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.visit(CodeShrinkVisitor.java:34)
            */
        public boolean onTransact(int r31, android.os.Parcel r32, android.os.Parcel r33, int r34) throws android.os.RemoteException {
            /*
                r30 = this;
                r13 = r30
                r14 = r31
                r15 = r32
                r11 = r33
                java.lang.String r12 = "com.android.internal.app.IPerfShielder"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r10 = 1
                if (r14 == r0) goto L_0x0453
                r9 = 0
                r0 = 0
                switch(r14) {
                    case 1: goto L_0x0417;
                    case 2: goto L_0x03e5;
                    case 3: goto L_0x03da;
                    case 4: goto L_0x03cb;
                    case 5: goto L_0x03b9;
                    case 6: goto L_0x039f;
                    case 7: goto L_0x0391;
                    case 8: goto L_0x037f;
                    case 9: goto L_0x036d;
                    case 10: goto L_0x0360;
                    case 11: goto L_0x034f;
                    case 12: goto L_0x032f;
                    case 13: goto L_0x0325;
                    case 14: goto L_0x0315;
                    case 15: goto L_0x0305;
                    case 16: goto L_0x02f7;
                    case 17: goto L_0x02e9;
                    case 18: goto L_0x02df;
                    case 19: goto L_0x02d1;
                    case 20: goto L_0x02a7;
                    case 21: goto L_0x0291;
                    case 22: goto L_0x0273;
                    case 23: goto L_0x0261;
                    case 24: goto L_0x0253;
                    case 25: goto L_0x022f;
                    case 26: goto L_0x0215;
                    case 27: goto L_0x01ff;
                    case 28: goto L_0x01e8;
                    case 29: goto L_0x0184;
                    case 30: goto L_0x0159;
                    case 31: goto L_0x0137;
                    case 32: goto L_0x00fb;
                    case 33: goto L_0x00c5;
                    case 34: goto L_0x00b8;
                    case 35: goto L_0x0099;
                    case 36: goto L_0x006d;
                    case 37: goto L_0x005b;
                    case 38: goto L_0x003d;
                    case 39: goto L_0x001a;
                    default: goto L_0x0015;
                }
            L_0x0015:
                boolean r0 = super.onTransact(r31, r32, r33, r34)
                return r0
            L_0x001a:
                r15.enforceInterface(r12)
                java.lang.Class r1 = r30.getClass()
                java.lang.ClassLoader r1 = r1.getClassLoader()
                java.util.HashMap r2 = r15.readHashMap(r1)
                int r3 = r32.readInt()
                if (r3 == 0) goto L_0x0038
                android.os.Parcelable$Creator r0 = android.content.pm.ActivityInfo.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r15)
                android.content.pm.ActivityInfo r0 = (android.content.pm.ActivityInfo) r0
                goto L_0x0039
            L_0x0038:
            L_0x0039:
                r13.setHapLinks(r2, r0)
                return r10
            L_0x003d:
                r15.enforceInterface(r12)
                int r1 = r32.readInt()
                if (r1 == 0) goto L_0x004f
                android.os.Parcelable$Creator r0 = android.content.Intent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r15)
                android.content.Intent r0 = (android.content.Intent) r0
                goto L_0x0050
            L_0x004f:
            L_0x0050:
                java.util.List r1 = r13.resolveQuickAppInfos(r0)
                r33.writeNoException()
                r11.writeTypedList(r1)
                return r10
            L_0x005b:
                r15.enforceInterface(r12)
                java.lang.String r0 = r32.readString()
                boolean r1 = r13.deleteFilterInfo(r0)
                r33.writeNoException()
                r11.writeInt(r1)
                return r10
            L_0x006d:
                r15.enforceInterface(r12)
                java.lang.String r1 = r32.readString()
                java.lang.String r2 = r32.readString()
                int r3 = r32.readInt()
                if (r3 == 0) goto L_0x0087
                android.os.Parcelable$Creator r0 = android.net.Uri.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r15)
                android.net.Uri r0 = (android.net.Uri) r0
                goto L_0x0088
            L_0x0087:
            L_0x0088:
                android.os.Parcelable$Creator r3 = android.os.Bundle.CREATOR
                java.util.ArrayList r3 = r15.createTypedArrayList(r3)
                boolean r4 = r13.insertFilterInfo(r1, r2, r0, r3)
                r33.writeNoException()
                r11.writeInt(r4)
                return r10
            L_0x0099:
                r15.enforceInterface(r12)
                java.lang.String r1 = r32.readString()
                int r2 = r32.readInt()
                if (r2 == 0) goto L_0x00af
                android.os.Parcelable$Creator r0 = android.content.Intent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r15)
                android.content.Intent r0 = (android.content.Intent) r0
                goto L_0x00b0
            L_0x00af:
            L_0x00b0:
                long r2 = r32.readLong()
                r13.reportNotificationClick(r1, r0, r2)
                return r10
            L_0x00b8:
                r15.enforceInterface(r12)
                android.os.Parcelable$Creator r0 = android.os.Bundle.CREATOR
                java.util.ArrayList r0 = r15.createTypedArrayList(r0)
                r13.reportExcessiveCpuUsageRecords(r0)
                return r10
            L_0x00c5:
                r15.enforceInterface(r12)
                int r1 = r32.readInt()
                if (r1 == 0) goto L_0x00d7
                android.os.Parcelable$Creator r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r15)
                android.os.Bundle r1 = (android.os.Bundle) r1
                goto L_0x00d8
            L_0x00d7:
                r1 = r0
            L_0x00d8:
                int r2 = r32.readInt()
                if (r2 == 0) goto L_0x00e8
                android.os.Parcelable$Creator r0 = android.os.statistics.E2EScenarioPayload.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r15)
                android.os.statistics.E2EScenarioPayload r0 = (android.os.statistics.E2EScenarioPayload) r0
            L_0x00e6:
                r2 = r0
                goto L_0x00e9
            L_0x00e8:
                goto L_0x00e6
            L_0x00e9:
                int r6 = r32.readInt()
                long r7 = r32.readLong()
                r0 = r13
                r3 = r6
                r4 = r7
                r0.finishSpecificScenario(r1, r2, r3, r4)
                r33.writeNoException()
                return r10
            L_0x00fb:
                r15.enforceInterface(r12)
                int r1 = r32.readInt()
                if (r1 == 0) goto L_0x010d
                android.os.Parcelable$Creator r1 = android.os.statistics.E2EScenario.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r15)
                android.os.statistics.E2EScenario r1 = (android.os.statistics.E2EScenario) r1
                goto L_0x010e
            L_0x010d:
                r1 = r0
            L_0x010e:
                java.lang.String r7 = r32.readString()
                int r2 = r32.readInt()
                if (r2 == 0) goto L_0x0122
                android.os.Parcelable$Creator r0 = android.os.statistics.E2EScenarioPayload.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r15)
                android.os.statistics.E2EScenarioPayload r0 = (android.os.statistics.E2EScenarioPayload) r0
            L_0x0120:
                r3 = r0
                goto L_0x0123
            L_0x0122:
                goto L_0x0120
            L_0x0123:
                int r8 = r32.readInt()
                long r16 = r32.readLong()
                r0 = r13
                r2 = r7
                r4 = r8
                r5 = r16
                r0.finishMatchingScenario(r1, r2, r3, r4, r5)
                r33.writeNoException()
                return r10
            L_0x0137:
                r15.enforceInterface(r12)
                int r1 = r32.readInt()
                if (r1 == 0) goto L_0x0149
                android.os.Parcelable$Creator r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r15)
                android.os.Bundle r0 = (android.os.Bundle) r0
                goto L_0x014a
            L_0x0149:
            L_0x014a:
                int r1 = r32.readInt()
                long r2 = r32.readLong()
                r13.abortSpecificScenario(r0, r1, r2)
                r33.writeNoException()
                return r10
            L_0x0159:
                r15.enforceInterface(r12)
                int r1 = r32.readInt()
                if (r1 == 0) goto L_0x016c
                android.os.Parcelable$Creator r0 = android.os.statistics.E2EScenario.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r15)
                android.os.statistics.E2EScenario r0 = (android.os.statistics.E2EScenario) r0
            L_0x016a:
                r1 = r0
                goto L_0x016d
            L_0x016c:
                goto L_0x016a
            L_0x016d:
                java.lang.String r6 = r32.readString()
                int r7 = r32.readInt()
                long r8 = r32.readLong()
                r0 = r13
                r2 = r6
                r3 = r7
                r4 = r8
                r0.abortMatchingScenario(r1, r2, r3, r4)
                r33.writeNoException()
                return r10
            L_0x0184:
                r15.enforceInterface(r12)
                int r1 = r32.readInt()
                if (r1 == 0) goto L_0x0196
                android.os.Parcelable$Creator r1 = android.os.statistics.E2EScenario.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r15)
                android.os.statistics.E2EScenario r1 = (android.os.statistics.E2EScenario) r1
                goto L_0x0197
            L_0x0196:
                r1 = r0
            L_0x0197:
                int r2 = r32.readInt()
                if (r2 == 0) goto L_0x01a6
                android.os.Parcelable$Creator r2 = android.os.statistics.E2EScenarioSettings.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r15)
                android.os.statistics.E2EScenarioSettings r2 = (android.os.statistics.E2EScenarioSettings) r2
                goto L_0x01a7
            L_0x01a6:
                r2 = r0
            L_0x01a7:
                java.lang.String r16 = r32.readString()
                int r3 = r32.readInt()
                if (r3 == 0) goto L_0x01bb
                android.os.Parcelable$Creator r0 = android.os.statistics.E2EScenarioPayload.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r15)
                android.os.statistics.E2EScenarioPayload r0 = (android.os.statistics.E2EScenarioPayload) r0
            L_0x01b9:
                r4 = r0
                goto L_0x01bc
            L_0x01bb:
                goto L_0x01b9
            L_0x01bc:
                int r17 = r32.readInt()
                long r18 = r32.readLong()
                int r0 = r32.readInt()
                if (r0 == 0) goto L_0x01cc
                r8 = r10
                goto L_0x01cd
            L_0x01cc:
                r8 = r9
            L_0x01cd:
                r0 = r13
                r3 = r16
                r5 = r17
                r6 = r18
                android.os.Bundle r0 = r0.beginScenario(r1, r2, r3, r4, r5, r6, r8)
                r33.writeNoException()
                if (r0 == 0) goto L_0x01e4
                r11.writeInt(r10)
                r0.writeToParcel(r11, r10)
                goto L_0x01e7
            L_0x01e4:
                r11.writeInt(r9)
            L_0x01e7:
                return r10
            L_0x01e8:
                r15.enforceInterface(r12)
                android.os.ParcelFileDescriptor r0 = r30.getPerfEventSocketFd()
                r33.writeNoException()
                if (r0 == 0) goto L_0x01fb
                r11.writeInt(r10)
                r0.writeToParcel(r11, r10)
                goto L_0x01fe
            L_0x01fb:
                r11.writeInt(r9)
            L_0x01fe:
                return r10
            L_0x01ff:
                r15.enforceInterface(r12)
                java.lang.String r0 = r32.readString()
                java.lang.String r1 = r32.readString()
                boolean r2 = r13.removeCallingPkgHookRule(r0, r1)
                r33.writeNoException()
                r11.writeInt(r2)
                return r10
            L_0x0215:
                r15.enforceInterface(r12)
                java.lang.String r0 = r32.readString()
                java.lang.String r1 = r32.readString()
                java.lang.String r2 = r32.readString()
                boolean r3 = r13.addCallingPkgHookRule(r0, r1, r2)
                r33.writeNoException()
                r11.writeInt(r3)
                return r10
            L_0x022f:
                r15.enforceInterface(r12)
                int r8 = r32.readInt()
                java.lang.String r9 = r32.readString()
                long r16 = r32.readLong()
                long r18 = r32.readLong()
                java.lang.String r20 = r32.readString()
                r0 = r13
                r1 = r8
                r2 = r9
                r3 = r16
                r5 = r18
                r7 = r20
                r0.reportAnr(r1, r2, r3, r5, r7)
                return r10
            L_0x0253:
                r15.enforceInterface(r12)
                long r0 = r30.getFreeMemory()
                r33.writeNoException()
                r11.writeLong(r0)
                return r10
            L_0x0261:
                r15.enforceInterface(r12)
                java.lang.String r0 = r32.readString()
                boolean r1 = r13.deletePackageInfo(r0)
                r33.writeNoException()
                r11.writeInt(r1)
                return r10
            L_0x0273:
                r15.enforceInterface(r12)
                int r1 = r32.readInt()
                if (r1 == 0) goto L_0x0285
                android.os.Parcelable$Creator r0 = android.content.pm.PackageInfo.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r15)
                android.content.pm.PackageInfo r0 = (android.content.pm.PackageInfo) r0
                goto L_0x0286
            L_0x0285:
            L_0x0286:
                boolean r1 = r13.insertPackageInfo(r0)
                r33.writeNoException()
                r11.writeInt(r1)
                return r10
            L_0x0291:
                r15.enforceInterface(r12)
                java.lang.String r0 = r32.readString()
                java.lang.String r1 = r32.readString()
                boolean r2 = r13.deleteRedirectRule(r0, r1)
                r33.writeNoException()
                r11.writeInt(r2)
                return r10
            L_0x02a7:
                r15.enforceInterface(r12)
                java.lang.String r1 = r32.readString()
                java.lang.String r2 = r32.readString()
                java.lang.String r3 = r32.readString()
                int r4 = r32.readInt()
                if (r4 == 0) goto L_0x02c5
                android.os.Parcelable$Creator r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r15)
                android.os.Bundle r0 = (android.os.Bundle) r0
                goto L_0x02c6
            L_0x02c5:
            L_0x02c6:
                boolean r4 = r13.insertRedirectRule(r1, r2, r3, r0)
                r33.writeNoException()
                r11.writeInt(r4)
                return r10
            L_0x02d1:
                r15.enforceInterface(r12)
                int r0 = r30.getMemoryTrimLevel()
                r33.writeNoException()
                r11.writeInt(r0)
                return r10
            L_0x02df:
                r15.enforceInterface(r12)
                r30.clearTimeConsumingIntent()
                r33.writeNoException()
                return r10
            L_0x02e9:
                r15.enforceInterface(r12)
                java.lang.String[] r0 = r32.createStringArray()
                r13.removeTimeConsumingIntent(r0)
                r33.writeNoException()
                return r10
            L_0x02f7:
                r15.enforceInterface(r12)
                java.lang.String[] r0 = r32.createStringArray()
                r13.addTimeConsumingIntent(r0)
                r33.writeNoException()
                return r10
            L_0x0305:
                r15.enforceInterface(r12)
                int r0 = r32.readInt()
                if (r0 == 0) goto L_0x0310
                r9 = r10
            L_0x0310:
                r0 = r9
                r13.setMiuiBroadcastDispatchEnable(r0)
                return r10
            L_0x0315:
                r15.enforceInterface(r12)
                int r0 = r32.readInt()
                if (r0 == 0) goto L_0x0320
                r9 = r10
            L_0x0320:
                r0 = r9
                r13.setMiuiContentProviderControl(r0)
                return r10
            L_0x0325:
                r15.enforceInterface(r12)
                r30.closeCheckPriority()
                r33.writeNoException()
                return r10
            L_0x032f:
                r15.enforceInterface(r12)
                int r1 = r32.readInt()
                if (r1 == 0) goto L_0x0341
                android.os.Parcelable$Creator r0 = com.android.internal.app.MiuiServicePriority.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r15)
                com.android.internal.app.MiuiServicePriority r0 = (com.android.internal.app.MiuiServicePriority) r0
                goto L_0x0342
            L_0x0341:
            L_0x0342:
                int r1 = r32.readInt()
                if (r1 == 0) goto L_0x034a
                r9 = r10
            L_0x034a:
                r1 = r9
                r13.removeServicePriority(r0, r1)
                return r10
            L_0x034f:
                r15.enforceInterface(r12)
                android.os.Parcelable$Creator r0 = com.android.internal.app.MiuiServicePriority.CREATOR
                java.util.ArrayList r0 = r15.createTypedArrayList(r0)
                long r1 = r32.readLong()
                r13.setServicePriorityWithNoProc(r0, r1)
                return r10
            L_0x0360:
                r15.enforceInterface(r12)
                android.os.Parcelable$Creator r0 = com.android.internal.app.MiuiServicePriority.CREATOR
                java.util.ArrayList r0 = r15.createTypedArrayList(r0)
                r13.setServicePriority(r0)
                return r10
            L_0x036d:
                r15.enforceInterface(r12)
                int[] r0 = r32.createIntArray()
                java.util.List r1 = r13.updateProcessPartialMemInfoByPids(r0)
                r33.writeNoException()
                r11.writeTypedList(r1)
                return r10
            L_0x037f:
                r15.enforceInterface(r12)
                int[] r0 = r32.createIntArray()
                java.util.List r1 = r13.updateProcessFullMemInfoByPids(r0)
                r33.writeNoException()
                r11.writeTypedList(r1)
                return r10
            L_0x0391:
                r15.enforceInterface(r12)
                java.util.List r0 = r30.getAllRunningProcessMemInfos()
                r33.writeNoException()
                r11.writeTypedList(r0)
                return r10
            L_0x039f:
                r15.enforceInterface(r12)
                int r0 = r32.readInt()
                int r1 = r32.readInt()
                int r2 = r32.readInt()
                java.lang.String r3 = r32.readString()
                r13.setForkedProcessGroup(r0, r1, r2, r3)
                r33.writeNoException()
                return r10
            L_0x03b9:
                r15.enforceInterface(r12)
                int r0 = r32.readInt()
                java.lang.String r1 = r13.getPackageNameByPid(r0)
                r33.writeNoException()
                r11.writeString(r1)
                return r10
            L_0x03cb:
                r15.enforceInterface(r12)
                int r0 = r32.readInt()
                int r1 = r32.readInt()
                r13.killUnusedApp(r0, r1)
                return r10
            L_0x03da:
                r15.enforceInterface(r12)
                int r0 = r32.readInt()
                r13.setSchedFgPid(r0)
                return r10
            L_0x03e5:
                r15.enforceInterface(r12)
                java.lang.String r16 = r32.readString()
                java.lang.String r17 = r32.readString()
                long r18 = r32.readLong()
                long r20 = r32.readLong()
                int r0 = r32.readInt()
                if (r0 == 0) goto L_0x0400
                r7 = r10
                goto L_0x0401
            L_0x0400:
                r7 = r9
            L_0x0401:
                int r0 = r32.readInt()
                if (r0 == 0) goto L_0x0409
                r8 = r10
                goto L_0x040a
            L_0x0409:
                r8 = r9
            L_0x040a:
                r0 = r13
                r1 = r16
                r2 = r17
                r3 = r18
                r5 = r20
                r0.addActivityLaunchTime(r1, r2, r3, r5, r7, r8)
                return r10
            L_0x0417:
                r15.enforceInterface(r12)
                int r16 = r32.readInt()
                int r17 = r32.readInt()
                java.lang.String r18 = r32.readString()
                long r19 = r32.readLong()
                long r21 = r32.readLong()
                long r23 = r32.readLong()
                int r25 = r32.readInt()
                long r26 = r32.readLong()
                r0 = r13
                r1 = r16
                r2 = r17
                r3 = r18
                r4 = r19
                r6 = r21
                r8 = r23
                r28 = r10
                r10 = r25
                r13 = r11
                r14 = r12
                r11 = r26
                r0.reportPerceptibleJank(r1, r2, r3, r4, r6, r8, r10, r11)
                return r28
            L_0x0453:
                r28 = r10
                r13 = r11
                r14 = r12
                r13.writeString(r14)
                return r28
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.app.IPerfShielder.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }
    }

    void abortMatchingScenario(E2EScenario e2EScenario, String str, int i, long j) throws RemoteException;

    void abortSpecificScenario(Bundle bundle, int i, long j) throws RemoteException;

    void addActivityLaunchTime(String str, String str2, long j, long j2, boolean z, boolean z2) throws RemoteException;

    boolean addCallingPkgHookRule(String str, String str2, String str3) throws RemoteException;

    void addTimeConsumingIntent(String[] strArr) throws RemoteException;

    Bundle beginScenario(E2EScenario e2EScenario, E2EScenarioSettings e2EScenarioSettings, String str, E2EScenarioPayload e2EScenarioPayload, int i, long j, boolean z) throws RemoteException;

    void clearTimeConsumingIntent() throws RemoteException;

    void closeCheckPriority() throws RemoteException;

    boolean deleteFilterInfo(String str) throws RemoteException;

    boolean deletePackageInfo(String str) throws RemoteException;

    boolean deleteRedirectRule(String str, String str2) throws RemoteException;

    void finishMatchingScenario(E2EScenario e2EScenario, String str, E2EScenarioPayload e2EScenarioPayload, int i, long j) throws RemoteException;

    void finishSpecificScenario(Bundle bundle, E2EScenarioPayload e2EScenarioPayload, int i, long j) throws RemoteException;

    List<Bundle> getAllRunningProcessMemInfos() throws RemoteException;

    long getFreeMemory() throws RemoteException;

    int getMemoryTrimLevel() throws RemoteException;

    String getPackageNameByPid(int i) throws RemoteException;

    ParcelFileDescriptor getPerfEventSocketFd() throws RemoteException;

    boolean insertFilterInfo(String str, String str2, Uri uri, List<Bundle> list) throws RemoteException;

    boolean insertPackageInfo(PackageInfo packageInfo) throws RemoteException;

    boolean insertRedirectRule(String str, String str2, String str3, Bundle bundle) throws RemoteException;

    void killUnusedApp(int i, int i2) throws RemoteException;

    boolean removeCallingPkgHookRule(String str, String str2) throws RemoteException;

    void removeServicePriority(MiuiServicePriority miuiServicePriority, boolean z) throws RemoteException;

    void removeTimeConsumingIntent(String[] strArr) throws RemoteException;

    void reportAnr(int i, String str, long j, long j2, String str2) throws RemoteException;

    void reportExcessiveCpuUsageRecords(List<Bundle> list) throws RemoteException;

    void reportNotificationClick(String str, Intent intent, long j) throws RemoteException;

    void reportPerceptibleJank(int i, int i2, String str, long j, long j2, long j3, int i3, long j4) throws RemoteException;

    List<QuickAppResolveInfo> resolveQuickAppInfos(Intent intent) throws RemoteException;

    void setForkedProcessGroup(int i, int i2, int i3, String str) throws RemoteException;

    void setHapLinks(Map map, ActivityInfo activityInfo) throws RemoteException;

    void setMiuiBroadcastDispatchEnable(boolean z) throws RemoteException;

    void setMiuiContentProviderControl(boolean z) throws RemoteException;

    void setSchedFgPid(int i) throws RemoteException;

    void setServicePriority(List<MiuiServicePriority> list) throws RemoteException;

    void setServicePriorityWithNoProc(List<MiuiServicePriority> list, long j) throws RemoteException;

    List<Bundle> updateProcessFullMemInfoByPids(int[] iArr) throws RemoteException;

    List<Bundle> updateProcessPartialMemInfoByPids(int[] iArr) throws RemoteException;
}

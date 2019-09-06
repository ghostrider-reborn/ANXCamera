package d.a;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: IPopupCameraManager */
public interface a extends IInterface {

    /* renamed from: d.a.a$a reason: collision with other inner class name */
    /* compiled from: IPopupCameraManager */
    public static class C0015a implements a {
        public boolean a(int i, int i2, String str) throws RemoteException {
            return false;
        }

        public IBinder asBinder() {
            return null;
        }

        public int getMotorStatus() throws RemoteException {
            return 0;
        }

        public void j() throws RemoteException {
        }

        public boolean popupMotor() throws RemoteException {
            return false;
        }

        public boolean takebackMotor() throws RemoteException {
            return false;
        }
    }

    /* compiled from: IPopupCameraManager */
    public static abstract class b extends Binder implements a {
        private static final String DESCRIPTOR = "miui.popupcamera.IPopupCameraManager";
        static final int Ia = 1;
        static final int Ja = 2;
        static final int Ka = 3;
        static final int La = 4;
        static final int Ma = 5;

        /* renamed from: d.a.a$b$a reason: collision with other inner class name */
        /* compiled from: IPopupCameraManager */
        private static class C0016a implements a {
            public static a sDefaultImpl;
            private IBinder mRemote;

            C0016a(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            public boolean a(int i, int i2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(b.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    boolean z = false;
                    if (!this.mRemote.transact(1, obtain, obtain2, 0) && b.getDefaultImpl() != null) {
                        return b.getDefaultImpl().a(i, i2, str);
                    }
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return b.DESCRIPTOR;
            }

            public int getMotorStatus() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(b.DESCRIPTOR);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0) && b.getDefaultImpl() != null) {
                        return b.getDefaultImpl().getMotorStatus();
                    }
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.recycle();
                    obtain.recycle();
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void j() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(b.DESCRIPTOR);
                    if (this.mRemote.transact(5, obtain, obtain2, 0) || b.getDefaultImpl() == null) {
                        obtain2.readException();
                        obtain2.recycle();
                        obtain.recycle();
                        return;
                    }
                    b.getDefaultImpl().j();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean popupMotor() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(b.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(2, obtain, obtain2, 0) && b.getDefaultImpl() != null) {
                        return b.getDefaultImpl().popupMotor();
                    }
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean takebackMotor() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(b.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(3, obtain, obtain2, 0) && b.getDefaultImpl() != null) {
                        return b.getDefaultImpl().takebackMotor();
                    }
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public b() {
            attachInterface(this, DESCRIPTOR);
        }

        public static boolean a(a aVar) {
            if (C0016a.sDefaultImpl != null || aVar == null) {
                return false;
            }
            C0016a.sDefaultImpl = aVar;
            return true;
        }

        public static a asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof a)) ? new C0016a(iBinder) : (a) queryLocalInterface;
        }

        public static a getDefaultImpl() {
            return C0016a.sDefaultImpl;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            int i3 = 0;
            String str = DESCRIPTOR;
            if (i == 1) {
                parcel.enforceInterface(str);
                boolean a2 = a(parcel.readInt(), parcel.readInt(), parcel.readString());
                parcel2.writeNoException();
                if (a2) {
                    i3 = 1;
                }
                parcel2.writeInt(i3);
                return true;
            } else if (i == 2) {
                parcel.enforceInterface(str);
                boolean popupMotor = popupMotor();
                parcel2.writeNoException();
                if (popupMotor) {
                    i3 = 1;
                }
                parcel2.writeInt(i3);
                return true;
            } else if (i == 3) {
                parcel.enforceInterface(str);
                boolean takebackMotor = takebackMotor();
                parcel2.writeNoException();
                if (takebackMotor) {
                    i3 = 1;
                }
                parcel2.writeInt(i3);
                return true;
            } else if (i == 4) {
                parcel.enforceInterface(str);
                int motorStatus = getMotorStatus();
                parcel2.writeNoException();
                parcel2.writeInt(motorStatus);
                return true;
            } else if (i == 5) {
                parcel.enforceInterface(str);
                j();
                parcel2.writeNoException();
                return true;
            } else if (i != 1598968902) {
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                parcel2.writeString(str);
                return true;
            }
        }
    }

    boolean a(int i, int i2, String str) throws RemoteException;

    int getMotorStatus() throws RemoteException;

    void j() throws RemoteException;

    boolean popupMotor() throws RemoteException;

    boolean takebackMotor() throws RemoteException;
}

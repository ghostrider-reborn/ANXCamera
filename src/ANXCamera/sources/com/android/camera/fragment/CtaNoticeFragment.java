package com.android.camera.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import com.android.camera.R;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.global.DataItemGlobal;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Locale;
import miui.app.AlertDialog;

public class CtaNoticeFragment extends DialogFragment {
    public static final String TAG = "CtaNoticeFragment";
    public static final int TYPE_LIVE_VIDEO = 1;
    public static final int TYPE_STICKER = 3;
    public static final int TYPE_VOICE_CAPTION = 2;
    private OnCtaNoticeClickListener mClickListener;
    private boolean mShowRemindButton;
    private int mType;

    public static class CTA {
        private static boolean sCanConnectToNetworkTemp = false;

        public static boolean canConnectNetwork() {
            if (sCanConnectToNetworkTemp) {
                return true;
            }
            return ((DataItemGlobal) DataRepository.provider().dataGlobal()).getCTACanCollect();
        }

        public static void setCanConnectNetwork(boolean z, boolean z2) {
            if (z) {
                ((DataItemGlobal) DataRepository.provider().dataGlobal()).setCTACanCollect(z2);
            } else {
                sCanConnectToNetworkTemp = z2;
            }
        }
    }

    public interface OnCtaNoticeClickListener {
        void onNegativeClick(DialogInterface dialogInterface, int i);

        void onPositiveClick(DialogInterface dialogInterface, int i);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    public CtaNoticeFragment(boolean z, OnCtaNoticeClickListener onCtaNoticeClickListener, int i) {
        this.mShowRemindButton = z;
        this.mClickListener = onCtaNoticeClickListener;
        this.mType = i;
    }

    public static boolean checkCta(FragmentManager fragmentManager, int i) {
        return checkCta(fragmentManager, true, i);
    }

    public static boolean checkCta(FragmentManager fragmentManager, boolean z, int i) {
        return checkCta(fragmentManager, z, (OnCtaNoticeClickListener) null, i);
    }

    public static boolean checkCta(FragmentManager fragmentManager, boolean z, OnCtaNoticeClickListener onCtaNoticeClickListener, int i) {
        return showCta(fragmentManager, z, onCtaNoticeClickListener, i) == null;
    }

    public static /* synthetic */ void lambda$onCreateDialog$0(CtaNoticeFragment ctaNoticeFragment, DialogInterface dialogInterface, int i) {
        CTA.setCanConnectNetwork(!ctaNoticeFragment.mShowRemindButton || ctaNoticeFragment.getDialog().isChecked(), true);
        if (ctaNoticeFragment.mClickListener != null) {
            ctaNoticeFragment.mClickListener.onPositiveClick(dialogInterface, i);
        }
    }

    public static /* synthetic */ void lambda$onCreateDialog$1(CtaNoticeFragment ctaNoticeFragment, DialogInterface dialogInterface, int i) {
        CTA.setCanConnectNetwork(!ctaNoticeFragment.mShowRemindButton || ctaNoticeFragment.getDialog().isChecked(), false);
        if (ctaNoticeFragment.mClickListener != null) {
            ctaNoticeFragment.mClickListener.onNegativeClick(dialogInterface, i);
        }
    }

    public static CtaNoticeFragment showCta(FragmentManager fragmentManager, boolean z, OnCtaNoticeClickListener onCtaNoticeClickListener, int i) {
        if (CTA.canConnectNetwork()) {
            return null;
        }
        Fragment findFragmentByTag = fragmentManager.findFragmentByTag(TAG);
        if (findFragmentByTag != null) {
            return (CtaNoticeFragment) findFragmentByTag;
        }
        CtaNoticeFragment ctaNoticeFragment = new CtaNoticeFragment(z, onCtaNoticeClickListener, i);
        ctaNoticeFragment.show(fragmentManager, TAG);
        return ctaNoticeFragment;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setCancelable(false);
    }

    public Dialog onCreateDialog(Bundle bundle) {
        Spanned spanned;
        String format = String.format("%s_%s", new Object[]{Locale.getDefault().getLanguage(), Locale.getDefault().getCountry()});
        String string = getString(R.string.link_user_agreement, new Object[]{format});
        String string2 = getString(R.string.link_privacy_policy, new Object[]{format});
        Log.d(TAG, "onCreateDialog: lang = " + format + ", linkUserAgreement = " + string + ", linkPrivacyPolicy = " + string2);
        switch (this.mType) {
            case 2:
                spanned = Html.fromHtml(getString(R.string.network_access_user_notice_message_voice_caption, new Object[]{string, string2}), 63);
                break;
            case 3:
                spanned = Html.fromHtml(getString(R.string.network_access_user_notice_message_sticker, new Object[]{string, string2}), 63);
                break;
            default:
                spanned = Html.fromHtml(getString(R.string.network_access_user_notice_message_live_video, new Object[]{string, string2}), 63);
                break;
        }
        Log.d(TAG, "onCreateDialog: messageRes = " + spanned);
        AlertDialog.Builder negativeButton = new AlertDialog.Builder(getActivity()).setTitle(R.string.network_access_user_notice_title).setMessage(spanned).setPositiveButton(R.string.network_access_user_notice_agree, new DialogInterface.OnClickListener() {
            public final void onClick(DialogInterface dialogInterface, int i) {
                CtaNoticeFragment.lambda$onCreateDialog$0(CtaNoticeFragment.this, dialogInterface, i);
            }
        }).setNegativeButton(17039360, new DialogInterface.OnClickListener() {
            public final void onClick(DialogInterface dialogInterface, int i) {
                CtaNoticeFragment.lambda$onCreateDialog$1(CtaNoticeFragment.this, dialogInterface, i);
            }
        });
        if (this.mShowRemindButton) {
            negativeButton.setCheckBox(true, getActivity().getString(R.string.do_not_remind_me));
        }
        return negativeButton.create();
    }

    public void onStart() {
        super.onStart();
        AlertDialog dialog = getDialog();
        if (dialog != null) {
            dialog.getMessageView().setMovementMethod(LinkMovementMethod.getInstance());
        }
    }
}

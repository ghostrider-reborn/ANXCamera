package com.ss.android.vesdk;

public class VEReverb2Params {
    public float bassb = 0.0f;
    public float basslpf = 1050.0f;
    public float damplpf = 18000.0f;
    public float delay = 0.0f;
    public float dry = 0.0f;
    public boolean enable = true;
    public boolean enableExciter = true;
    public float ereffactor = 1.0f;
    public float erefwet = 0.0f;
    public float erefwidth = 0.0f;
    public float ertolate = 0.0f;
    public float inputlpf = 18000.0f;
    public float outputlpf = 18000.0f;
    public int oversamplefactor = 1;
    public int rate = 44100;
    public float rt60 = 0.1f;
    public float spin = 0.0f;
    public float wander = 0.1f;
    public float wet = 0.0f;
    public float width = 0.0f;

    public static class VEPresets {
        public static final VEReverb2Params CRISTAL = new VEReverb2Params();
        public static final VEReverb2Params KTV = new VEReverb2Params();
        public static final VEReverb2Params NONE = new VEReverb2Params();
        public static final VEReverb2Params POP = new VEReverb2Params();
        public static final VEReverb2Params ROCK = new VEReverb2Params();

        static {
            NONE.enable = false;
            POP.rate = 44100;
            POP.oversamplefactor = 2;
            POP.ertolate = 0.25f;
            POP.erefwet = -26.0f;
            POP.dry = -10.0f;
            POP.ereffactor = 0.9f;
            POP.erefwidth = -0.68f;
            POP.width = 0.22f;
            POP.wet = -11.66f;
            POP.wander = 0.18f;
            POP.bassb = 0.07f;
            POP.spin = 4.57f;
            POP.inputlpf = 18000.0f;
            POP.basslpf = 93.0f;
            POP.damplpf = 14570.0f;
            POP.outputlpf = 17140.0f;
            POP.rt60 = 3.9999998f;
            POP.delay = 0.19f;
            KTV.rate = 44100;
            KTV.oversamplefactor = 2;
            KTV.ertolate = 0.1f;
            KTV.erefwet = -28.0f;
            KTV.dry = -7.0f;
            KTV.ereffactor = 1.3199999f;
            KTV.erefwidth = 0.110000014f;
            KTV.width = 0.42f;
            KTV.wet = -15.0f;
            KTV.wander = 0.38f;
            KTV.bassb = 0.075f;
            KTV.spin = 7.3f;
            KTV.inputlpf = 9560.001f;
            KTV.basslpf = 136.0f;
            KTV.damplpf = 11690.0f;
            KTV.outputlpf = 7100.0f;
            KTV.rt60 = 3.9f;
            KTV.delay = -0.42000002f;
            ROCK.rate = 44100;
            ROCK.oversamplefactor = 2;
            ROCK.ertolate = 0.0f;
            ROCK.erefwet = -26.0f;
            ROCK.dry = -8.0f;
            ROCK.ereffactor = 1.36f;
            ROCK.erefwidth = 1.0f;
            ROCK.width = 0.81f;
            ROCK.wet = -22.0f;
            ROCK.wander = 0.495f;
            ROCK.bassb = 0.02f;
            ROCK.spin = 7.0f;
            ROCK.inputlpf = 18000.0f;
            ROCK.basslpf = 84.0f;
            ROCK.damplpf = 18000.0f;
            ROCK.outputlpf = 18000.0f;
            ROCK.rt60 = 3.9f;
            ROCK.delay = -0.00999999f;
            CRISTAL.rate = 44100;
            CRISTAL.oversamplefactor = 2;
            CRISTAL.ertolate = 0.0f;
            CRISTAL.erefwet = -42.0f;
            CRISTAL.dry = -19.0f;
            CRISTAL.ereffactor = 0.5f;
            CRISTAL.erefwidth = 1.0f;
            CRISTAL.width = 0.81f;
            CRISTAL.wet = -12.0f;
            CRISTAL.wander = 0.17f;
            CRISTAL.bassb = 0.0f;
            CRISTAL.spin = 0.0f;
            CRISTAL.inputlpf = 5890.0f;
            CRISTAL.basslpf = 143.0f;
            CRISTAL.damplpf = 5690.0f;
            CRISTAL.outputlpf = 7650.0f;
            CRISTAL.rt60 = 3.6f;
            CRISTAL.delay = 0.5f;
        }
    }

    public static VEReverb2Params fromString(String str) {
        String[] split = str.split(",");
        try {
            VEReverb2Params vEReverb2Params = new VEReverb2Params();
            boolean z = false;
            vEReverb2Params.enableExciter = Integer.parseInt(split[0]) == 1;
            if (Integer.parseInt(split[1]) == 1) {
                z = true;
            }
            vEReverb2Params.enable = z;
            vEReverb2Params.rate = Integer.parseInt(split[2]);
            vEReverb2Params.oversamplefactor = Integer.parseInt(split[3]);
            vEReverb2Params.ertolate = Float.parseFloat(split[4]);
            vEReverb2Params.erefwet = Float.parseFloat(split[5]);
            vEReverb2Params.dry = Float.parseFloat(split[6]);
            vEReverb2Params.ereffactor = Float.parseFloat(split[7]);
            vEReverb2Params.erefwidth = Float.parseFloat(split[8]);
            vEReverb2Params.width = Float.parseFloat(split[9]);
            vEReverb2Params.wet = Float.parseFloat(split[10]);
            vEReverb2Params.wander = Float.parseFloat(split[11]);
            vEReverb2Params.bassb = Float.parseFloat(split[12]);
            vEReverb2Params.spin = Float.parseFloat(split[13]);
            vEReverb2Params.inputlpf = Float.parseFloat(split[14]);
            vEReverb2Params.basslpf = Float.parseFloat(split[15]);
            vEReverb2Params.damplpf = Float.parseFloat(split[16]);
            vEReverb2Params.outputlpf = Float.parseFloat(split[17]);
            vEReverb2Params.rt60 = Float.parseFloat(split[18]);
            vEReverb2Params.delay = Float.parseFloat(split[19]);
            return vEReverb2Params;
        } catch (Exception e) {
            return null;
        }
    }

    public VEReverb2Params copy() {
        return fromString(getParamsAsString());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        VEReverb2Params vEReverb2Params = (VEReverb2Params) obj;
        return this.enableExciter == vEReverb2Params.enableExciter && this.enable == vEReverb2Params.enable && this.rate == vEReverb2Params.rate && this.oversamplefactor == vEReverb2Params.oversamplefactor && Float.compare(vEReverb2Params.ertolate, this.ertolate) == 0 && Float.compare(vEReverb2Params.erefwet, this.erefwet) == 0 && Float.compare(vEReverb2Params.dry, this.dry) == 0 && Float.compare(vEReverb2Params.ereffactor, this.ereffactor) == 0 && Float.compare(vEReverb2Params.erefwidth, this.erefwidth) == 0 && Float.compare(vEReverb2Params.width, this.width) == 0 && Float.compare(vEReverb2Params.wet, this.wet) == 0 && Float.compare(vEReverb2Params.wander, this.wander) == 0 && Float.compare(vEReverb2Params.bassb, this.bassb) == 0 && Float.compare(vEReverb2Params.spin, this.spin) == 0 && Float.compare(vEReverb2Params.inputlpf, this.inputlpf) == 0 && Float.compare(vEReverb2Params.basslpf, this.basslpf) == 0 && Float.compare(vEReverb2Params.damplpf, this.damplpf) == 0 && Float.compare(vEReverb2Params.outputlpf, this.outputlpf) == 0 && Float.compare(vEReverb2Params.rt60, this.rt60) == 0 && Float.compare(vEReverb2Params.delay, this.delay) == 0;
    }

    public String getParamsAsString() {
        return (this.enableExciter ? 1 : 0) + "," + (this.enable ? 1 : 0) + "," + this.rate + "," + this.oversamplefactor + "," + this.ertolate + "," + this.erefwet + "," + this.dry + "," + this.ereffactor + "," + this.erefwidth + "," + this.width + "," + this.wet + "," + this.wander + "," + this.bassb + "," + this.spin + "," + this.inputlpf + "," + this.basslpf + "," + this.damplpf + "," + this.outputlpf + "," + this.rt60 + "," + this.delay;
    }

    public String toString() {
        return "Reverb2Params{enableExciter=" + this.enableExciter + "enable=" + this.enable + "rate=" + this.rate + ", oversamplefactor=" + this.oversamplefactor + ", ertolate=" + this.ertolate + ", erefwet=" + this.erefwet + ", dry=" + this.dry + ", ereffactor=" + this.ereffactor + ", erefwidth=" + this.erefwidth + ", width=" + this.width + ", wet=" + this.wet + ", wander=" + this.wander + ", bassb=" + this.bassb + ", spin=" + this.spin + ", inputlpf=" + this.inputlpf + ", basslpf=" + this.basslpf + ", damplpf=" + this.damplpf + ", outputlpf=" + this.outputlpf + ", rt60=" + this.rt60 + ", delay=" + this.delay + '}';
    }
}

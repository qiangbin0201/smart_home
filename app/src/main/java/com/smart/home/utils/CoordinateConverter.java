package com.smart.home.utils;

/**
 * Created by hesc on 15/6/5.
 * 经纬度坐标转换类，可以实现百度经纬度坐标(bd09ll)、百度墨卡托坐标(bd09mc)、
 * 经过国测局加密的坐标(gcj02)和gps获取的坐标(wgs84)之间的转换
 */
public class CoordinateConverter {
    /**
     * 坐标类型
     */
    public enum CoordinateKind{
        /**
         * 百度经纬度坐标
         */
        bd09ll,
        /**
         * 百度墨卡托坐标
         */
        bd09mc,
        /**
         * 经过国测局加密的坐标
         */
        gcj02,
        /**
         * gps获取的坐标
         */
        wgs84
    }

    /**
     * 经纬度坐标类
     */
    public static class LatLng{
        /**
         * 纬度
         */
        public double lat;
        /**
         * 经度
         */
        public double lng;

        public LatLng(){

        }

        public LatLng(LatLng latLng){
            this.lat = latLng.lat;
            this.lng = latLng.lng;
        }

        public LatLng(double lat, double lng){
            this.lat = lat;
            this.lng = lng;
        }

        @Override
        public boolean equals(Object o) {
            if(!(o instanceof LatLng)) return false;
            LatLng latLng = (LatLng) o;
            if(latLng == null) return false;

            return this.lat == latLng.lat && this.lng == latLng.lng;
        }

        @Override
        public String toString() {
            return "纬度:"+ lat +",经度:" + lng;
        }
    }

    /**
     * 输出结果
     * @param <T>
     */
    private class Output<T>{
        T lat;
        T lng;
    }

    /**
     * gps坐标加密类
     */
    private class GcjEncryptor{
        private double casm_rr;
        private long casm_t1;
        private long casm_t2;
        private double casm_x1;
        private double casm_y1;
        private double casm_x2;
        private double casm_y2;
        private double casm_f;

        private long _iix_, _iiy_;
        private static final int _COFF_ = 3686400;

        double yj_sin2(double x){
            double tt;
            double ss;
            int ff;
            double s2;
            int cc;
            ff = 0;
            if (x < 0) {
                x = -x;
                ff = 1;
            }
            cc = (int)(x / 6.28318530717959);
            tt = x - cc * 6.28318530717959;
            if (tt > 3.1415926535897932) {
                tt = tt - 3.1415926535897932;
                if (ff == 1)
                    ff = 0;
                else if (ff == 0)
                    ff = 1;
            }
            x = tt;
            ss = x;
            s2 = x;
            tt = tt * tt;
            s2 = s2 * tt;
            ss = ss - s2 * 0.166666666666667;
            s2 = s2 * tt;
            ss = ss + s2 * 8.33333333333333E-03;
            s2 = s2 * tt;
            ss = ss - s2 * 1.98412698412698E-04;
            s2 = s2 * tt;
            ss = ss + s2 * 2.75573192239859E-06;
            s2 = s2 * tt;
            ss = ss - s2 * 2.50521083854417E-08;
            if (ff == 1)
                ss = -ss;
            return ss;
        }

        double Transform_yj5(double x, double y) {
            double tt;
            tt = 300 + 1 * x + 2 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.sqrt(x
                    * x));
            tt = tt + (20 * yj_sin2(18.849555921538764 * x) + 20 * yj_sin2(
                    6.283185307179588 * x)) * 0.6667;
            tt = tt + (20 * yj_sin2(3.141592653589794 * x) + 40 * yj_sin2(
                    1.047197551196598 * x)) * 0.6667;
            tt = tt + (150 * yj_sin2(0.2617993877991495 * x) + 300 * yj_sin2(
                    0.1047197551196598 * x)) * 0.6667;
            return tt;
        }
        double Transform_yjy5(double x, double y){
            double tt;
            tt = -100 + 2 * x + 3 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.sqrt(x
                    * x));
            tt = tt + (20 * yj_sin2(18.849555921538764 * x) + 20 * yj_sin2(
                    6.283185307179588 * x)) * 0.6667;
            tt = tt + (20 * yj_sin2(3.141592653589794 * y) + 40 * yj_sin2(
                    1.047197551196598 * y)) * 0.6667;
            tt = tt + (160 * yj_sin2(0.2617993877991495 * y) + 320 * yj_sin2(
                    0.1047197551196598 * y)) * 0.6667;
            return tt;
        }

        double Transform_jy5(double x, double xx){
            double n;
            double a;
            double e;
            a = 6378245;
            e = 0.00669342;
            n = Math.sqrt(1 - e * yj_sin2(x * 0.0174532925199433) * yj_sin2(x
                    * 0.0174532925199433));
            n = (xx * 180) / (a / n * Math.cos(x * 0.0174532925199433) * 3.1415926);
            return n;
        }

        double Transform_jyj5(double x, double yy){
            double m;
            double a;
            double e;
            double mm;
            a = 6378245;
            e = 0.00669342;
            mm = 1 - e * yj_sin2(x * 0.0174532925199433) * yj_sin2(x
                    * 0.0174532925199433);
            m = (a * (1 - e)) / (mm * Math.sqrt(mm));
            return (yy * 180) / (m * 3.1415926);
        }

        double r_yj(){
            int casm_a;
            int casm_c;
            casm_a = 314159269;
            casm_c = 453806245;
            return 0;
        }

        double random_yj(){int tt;
            int t;
            int casm_a;
            int casm_c;
            casm_a = 314159269;
            casm_c = 453806245;
            casm_rr = casm_a * casm_rr + casm_c;
            t = (int)(casm_rr / 2);
            casm_rr = casm_rr - t * 2;
            casm_rr = casm_rr / 2;
            return (casm_rr);
        }

        void IniCasm(long w_time, long w_lng, long w_lat){
            int tt;
            casm_t1 = w_time;
            casm_t2 = w_time;
            tt = (int)(w_time / 0.357);
            casm_rr = w_time - tt * 0.357;
            if (w_time == 0)
                casm_rr = 0.3;
            casm_x1 = w_lng;
            casm_y1 = w_lat;
            casm_x2 = w_lng;
            casm_y2 = w_lat;
            casm_f = 3;
        }

        int wgtochina_lb(int wg_flag, long wg_lng, long wg_lat, int wg_heit, int wg_week, long wg_time, Output<Long> china){
            double x_add;
            double y_add;
            double h_add;
            double x_l;
            double y_l;
            double casm_v;
            double t1_t2;
            double x1_x2;
            double y1_y2;

            if (wg_heit > 5000) {
                china.lat = 0L;
                china.lng = 0L;
                return 0xFFFF95FF;
            }
            x_l = wg_lng;
            x_l = x_l / 3686400.0;
            y_l = wg_lat;
            y_l = y_l / 3686400.0;
            if (x_l < 72.004) {
                china.lat = 0L;
                china.lng = 0L;
                return 0xFFFF95FF;
            }
            if (x_l > 137.8347) {
                china.lat = 0L;
                china.lng = 0L;
                return 0xFFFF95FF;
            }
            if (y_l < 0.8293) {
                china.lat = 0L;
                china.lng = 0L;
                return 0xFFFF95FF;
            }
            if (y_l > 55.8271) {
                china.lat = 0L;
                china.lng = 0L;
                return 0xFFFF95FF;
            }
            //if (wg_flag == 0)
            IniCasm(wg_time, wg_lng, wg_lat);
            if(false)
            {
                IniCasm(wg_time, wg_lng, wg_lat);
                china.lng = wg_lng;
                china.lat = wg_lat;
                return 0x00000000;
            }

            casm_t2 = wg_time;
            t1_t2 = (double) (casm_t2 - casm_t1) / 1000.0;
            if (t1_t2 <= 0) {
                casm_t1 = casm_t2;
                casm_f = casm_f + 1;
                casm_x1 = casm_x2;
                casm_f = casm_f + 1;
                casm_y1 = casm_y2;
                casm_f = casm_f + 1;
            } else {
                if (t1_t2 > 120) {
                    if (casm_f == 3) {
                        casm_f = 0;
                        casm_x2 = wg_lng;
                        casm_y2 = wg_lat;
                        x1_x2 = casm_x2 - casm_x1;
                        y1_y2 = casm_y2 - casm_y1;
                        casm_v = Math.sqrt(x1_x2 * x1_x2 + y1_y2 * y1_y2) / t1_t2;
                        if (casm_v > 3185) {
                            china.lat = 0L;
                            china.lng = 0L;
                            return (0xFFFF95FF);
                        }

                    }
                    casm_t1 = casm_t2;
                    casm_f = casm_f + 1;
                    casm_x1 = casm_x2;
                    casm_f = casm_f + 1;
                    casm_y1 = casm_y2;
                    casm_f = casm_f + 1;
                }
            }
            x_add = Transform_yj5(x_l - 105, y_l - 35);
            y_add = Transform_yjy5(x_l - 105, y_l - 35);
            h_add = wg_heit;

            x_add = x_add + h_add * 0.001 + yj_sin2(wg_time * 0.0174532925199433)
                    + random_yj();
            y_add = y_add + h_add * 0.001 + yj_sin2(wg_time * 0.0174532925199433)
                    + random_yj();
            china.lat = (long)((x_l + Transform_jy5(y_l, x_add)) * 3686400);
            china.lng = (long)((y_l + Transform_jyj5(y_l, y_add)) * 3686400);
            return (0x00000000);
        }

        public GcjEncryptor() {
            casm_rr = 0;
            casm_t1 = 0;
            casm_t2 = 0;
            casm_x1 = 0;
            casm_y1 = 0;
            casm_x2 = 0;
            casm_y2 = 0;
            casm_f = 0;
        }

        int encrypt(LatLng pt, LatLng res) {
            if(res == null) {
                return -1;
            }
            _iix_ = (long)(pt.lat * _COFF_);
            _iiy_ = (long)(pt.lng * _COFF_);
            Output<Long> ioxy = new Output<>();
            if(this.wgtochina_lb(1, _iix_, _iiy_, 1, 0, 0, ioxy)!=0) {
                return -2;
            }
            res.lat = (double)(ioxy.lat) / (double)(_COFF_);
            res.lng = (double)(ioxy.lng) / (double)(_COFF_);
            return 0;
        }

        int encrypt(double oldx, double oldy, Output<Double> newxy) {
            _iix_ = (long)(oldx * _COFF_);
            _iiy_ = (long)(oldy * _COFF_);

            Output<Long> ioxy = new Output<>();
            if(this.wgtochina_lb(1, _iix_, _iiy_, 1,  0, 0, ioxy)!=0) {
                return -2;
            }
            newxy.lat = (double)(ioxy.lat) / (double)(_COFF_);
            newxy.lng = (double)(ioxy.lng) / (double)(_COFF_);
            return 0;
        }
    }

    private static final double _GRID_RADIX_ = 3E3;
    private static final double _MAX_dR_ = 2E-5;
    private static final double _MAX_dT_ = 3E-6;
    private static final double _LL2RAD_ = 0.0174532925194;
    private static final double _OFFSET_X_ = 0.0065;
    private static final double _OFFSET_Y_ = 0.0060;

    /**
     * 地球半径，单位：千米
     */
    private static double EARTHRADIUS = 6370996.81;
    /**
     * 百度墨卡托坐标边界值
     */
    private static double[] MCBAND = new double[] { 12890594.86, 8362377.87, 5591021, 3481989.83, 1678043.12, 0 };
    /**
     * 百度经纬度坐标边界值
     */
    private static double[] LLBAND = new double[] { 75, 60, 45, 30, 15, 0 };

    /**
     * 百度墨卡托坐标转成百度经纬度坐标参数
     */
    private static double[][] MC2LL = new double[][] { { 1.410526172116255e-008, 8.983055096488720e-006,
            -1.99398338163310, 2.009824383106796e+002, -1.872403703815547e+002,
            91.60875166698430, -23.38765649603339, 2.57121317296198,
            -0.03801003308653, 1.733798120000000e+007 }, { -7.435856389565537e-009,
            8.983055097726239e-006, -0.78625201886289, 96.32687599759846,
            -1.85204757529826, -59.36935905485877, 47.40033549296737,
            -16.50741931063887, 2.28786674699375, 1.026014486000000e+007 }, {
        -3.030883460898826e-008, 8.983055099835780e-006, 0.30071316287616,
                59.74293618442277, 7.35798407487100, -25.38371002664745,
                13.45380521110908, -3.29883767235584, 0.32710905363475,
                6.856817370000000e+006 }, { -1.981981304930552e-008,
            8.983055099779535e-006, 0.03278182852591, 40.31678527705744,
            0.65659298677277, -4.44255534477492, 0.85341911805263,
            0.12923347998204, -0.04625736007561, 4.482777060000000e+006 }, {
        3.091913710684370e-009, 8.983055096812155e-006, 0.00006995724062,
                23.10934304144901, -0.00023663490511, -0.63218178102420,
                -0.00663494467273, 0.03430082397953, -0.00466043876332,
                2.555164400000000e+006 }, { 2.890871144776878e-009,
            8.983055095805407e-006, -0.00000003068298, 7.47137025468032,
            -0.00000353937994, -0.02145144861037, -0.00001234426596,
            0.00010322952773, -0.00000323890364, 8.260885000000000e+005 } };

    /**
     * 百度经纬度坐标转成百度墨卡托坐标参数
     */
    private static double[][] LL2MC = new double[][]{ { -0.00157021024440, 1.113207020616939e+005,
            1.704480524535203e+015, -1.033898737604234e+016,
            2.611266785660388e+016, -3.514966917665370e+016,
            2.659570071840392e+016, -1.072501245418824e+016,
            1.800819912950474e+015, 82.50000000000000 }, { 8.277824516172526e-004,
            1.113207020463578e+005, 6.477955746671608e+008,
            -4.082003173641316e+009, 1.077490566351142e+010,
            -1.517187553151559e+010, 1.205306533862167e+010,
            -5.124939663577472e+009, 9.133119359512032e+008, 67.50000000000000 }, {
        0.00337398766765, 1.113207020202162e+005, 4.481351045890365e+006,
                -2.339375119931662e+007, 7.968221547186455e+007,
                -1.159649932797253e+008, 9.723671115602145e+007,
                -4.366194633752821e+007, 8.477230501135234e+006, 52.50000000000000 }, {
        0.00220636496208, 1.113207020209128e+005, 5.175186112841131e+004,
                3.796837749470245e+006, 9.920137397791013e+005,
                -1.221952217112870e+006, 1.340652697009075e+006,
                -6.209436990984312e+005, 1.444169293806241e+005, 37.50000000000000 }, {
        -3.441963504368392e-004, 1.113207020576856e+005,
                2.782353980772752e+002, 2.485758690035394e+006, 6.070750963243378e+003,
                5.482118345352118e+004, 9.540606633304236e+003,
                -2.710553267466450e+003, 1.405483844121726e+003, 22.50000000000000 },
        {-3.218135878613132e-004, 1.113207020701615e+005, 0.00369383431289,
                8.237256402795718e+005, 0.46104986909093,
                2.351343141331292e+003, 1.58060784298199, 8.77738589078284,
                0.37238884252424, 7.45000000000000 } };



    //原经纬度坐标
    private LatLng mFromLatLng;
    //原经纬度坐标类型
    private CoordinateKind mFromCoordinateKind;
    //目标经纬度坐标类型
    private CoordinateKind mToCoordinateKind;
    //GPS加密类
    private GcjEncryptor mGcjEncryptor = new GcjEncryptor();

    /**
     * 设置原经纬度坐标
     * @param latLng
     * @return
     */
    public CoordinateConverter fromLatLng(LatLng latLng){
        mFromLatLng = latLng;
        return this;
    }

    /**
     * 设置原经纬度坐标类型
     * @param kind
     * @return
     */
    public CoordinateConverter fromKind(CoordinateKind kind){
        mFromCoordinateKind = kind;
        return this;
    }

    /**
     * 设置转换后经纬度坐标类型
     * @param kind
     * @return
     */
    public CoordinateConverter toKind(CoordinateKind kind){
        mToCoordinateKind = kind;
        return this;
    }

    /**
     * 获取转换后的经纬度坐标
     * @return
     */
    public LatLng convert(){
        //校验参数
        validArgs();
        //经纬度坐标类型一样，直接返回
        if(mFromCoordinateKind.equals(mToCoordinateKind)){
            return mFromLatLng;
        }

        LatLng oldLatLng = new LatLng(mFromLatLng);
        if(mFromCoordinateKind == CoordinateKind.bd09mc){
            oldLatLng = mc2ll(oldLatLng);
        }
        if(mFromCoordinateKind == CoordinateKind.wgs84){
            if (mGcjEncryptor.encrypt(oldLatLng, oldLatLng) != 0) {
                return null;
            }
        } else if(mFromCoordinateKind == CoordinateKind.bd09mc ||
                mFromCoordinateKind == CoordinateKind.bd09ll) {
            if(bd_decrypt(oldLatLng, oldLatLng) != 0){
                return null;
            }
        }

        if(mToCoordinateKind == CoordinateKind.wgs84){
            if(gcj_decrypt(oldLatLng, oldLatLng) != 0){
                return null;
            }
        } else if(mToCoordinateKind == CoordinateKind.bd09mc ||
                mToCoordinateKind == CoordinateKind.bd09ll) {
            if(bd_encrypt(oldLatLng, oldLatLng) != 0){
                return null;
            }
        }

        if(mToCoordinateKind == CoordinateKind.bd09mc){
            return ll2mc(oldLatLng);
        } else {
            return oldLatLng;
        }
    }

    /**
     * 百度墨卡托坐标转成百度经纬度坐标
     * @param fromPoint
     * @return
     */
    private LatLng mc2ll(LatLng fromPoint){
        LatLng temp = new LatLng();
        temp.lat = fromPoint.lat;
        if(temp.lat > 20037508.342) {
            temp.lat = 20037508.342;
        } else if(temp.lat < -20037508.342) {
            temp.lat = -20037508.342;
        }
        temp.lng = fromPoint.lng;
        if (temp.lng < 1E-6 && temp.lng >= 0) {
            temp.lng = 1E-6;
        } else if(temp.lng < 0 && temp.lng > -1.0E-6) {
            temp.lng = -1E-6;
        } else if(temp.lng > 20037508.342) {
            temp.lng = 20037508.342;
        } else if(temp.lng < -20037508.342) {
            temp.lng = -20037508.342;
        }

        double[] factor = new double[10];
        for (int i = 0; i < MCBAND.length; i++) {
            if (Math.abs(temp.lng) > MCBAND[i]) {
                System.arraycopy(MC2LL[i], 0, factor, 0, factor.length);
                break;
            }
        }
        return _conv_(temp, factor);
    }

    /**
     * 百度经纬度坐标转成百度墨卡托坐标
     * @param fromPoint
     * @return
     */
    private LatLng ll2mc(LatLng fromPoint) {
        LatLng temp = new LatLng();
        temp.lat = fromPoint.lat;
        if(temp.lat > 180.0) {
            temp.lat = 180.0;
        } else if(temp.lat < -180.0) {
            temp.lat = -180.0;
        }

        temp.lng = fromPoint.lng;
        if (temp.lng < 1E-7 && temp.lng >= 0.0) {
            temp.lng = 1E-7;
        } else if(temp.lng < 0 && temp.lng > -1.0E-7) {
            temp.lng = -1E-7;
        } else if(temp.lng > 74) {
            temp.lng = 74;
        } else if(temp.lng < -74) {
            temp.lng = -74;
        }

        double[] factor = new double[10];
        for (int i = 0; i < LLBAND.length ; i++) {
            if (Math.abs(temp.lng) > LLBAND[i]) {
                System.arraycopy(LL2MC[i], 0, factor, 0, factor.length);
                break;
            }
        }
        return _conv_(temp, factor);
    }

    /**
     * 百度墨卡托坐标和百度经纬度坐标中，根据指定的因子(factor)把原坐标转成目标坐标
     * @param fromPoint
     * @param factor
     * @return
     */
    private LatLng _conv_(LatLng fromPoint, double[] factor) {
        LatLng toPoint = new LatLng();
        toPoint.lat = factor[0] + factor[1] * Math.abs(fromPoint.lat);
        double temp = Math.abs(fromPoint.lng) / factor[9];
        toPoint.lng = factor[2] + factor[3] * temp + factor[4] * temp * temp
                + factor[5] * temp * temp * temp + factor[6] * temp * temp * temp
                * temp + factor[7] * temp * temp * temp * temp * temp + factor[8]
                * temp * temp * temp * temp * temp * temp;
        toPoint.lat *= (fromPoint.lat < 0 ? -1 : 1);
        toPoint.lng *= (fromPoint.lng < 0 ? -1 : 1);
        return toPoint;
    }

    /**
     * 计算距离
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    private double dis(double x1,double y1,double x2,double y2) {
        return Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
    }

    /**
     * gps坐标解密
     * @param oldxy
     * @return
     */
    private int gcj_decrypt(LatLng oldxy, LatLng newxy) {
        if (newxy == null) {
            return -1;
        }
        double xmid = oldxy.lat, ymid = oldxy.lng;
        double cellsize = 1;
        double realgcjx = oldxy.lat, realgcjy = oldxy.lng;
        double gpsx,gpsy;
        int res = -1;
        Output<Double> xymidnew = new Output<>();
        mGcjEncryptor.encrypt(xmid,ymid,xymidnew);
        if (dis(xymidnew.lat,xymidnew.lng,realgcjx,realgcjy) <= 1e-6) {
            gpsx = xmid;
            gpsy = ymid;
            res = 0;
        } else {
            while(true) {
                double x1 = xmid - cellsize;
                double y1 = ymid + cellsize;
                double x2 = xmid - cellsize;
                double y2 = ymid - cellsize;
                double x3 = xmid + cellsize;
                double y3 = ymid - cellsize;
                double x4 = xmid + cellsize;
                double y4 = ymid + cellsize;
                Output<Double> xy1new = new Output<>();
                Output<Double> xy2new = new Output<>();
                Output<Double> xy3new = new Output<>();
                Output<Double> xy4new = new Output<>();

                mGcjEncryptor.encrypt(x1,y1,xy1new);
                mGcjEncryptor.encrypt(x2,y2,xy2new);
                mGcjEncryptor.encrypt(x3,y3,xy3new);
                mGcjEncryptor.encrypt(x4,y4,xy4new);
                double dis1 = dis(xy1new.lat,xy1new.lng,realgcjx,realgcjy);
                double dis2 = dis(xy2new.lat,xy2new.lng,realgcjx,realgcjy);
                double dis3 = dis(xy3new.lat,xy3new.lng,realgcjx,realgcjy);
                double dis4 = dis(xy4new.lat,xy4new.lng,realgcjx,realgcjy);
                if (dis1 < 1e-6) {
                    gpsx = x1;
                    gpsy = y1;
                    res = 0;
                    break;
                }
                if (dis2 < 1e-6) {
                    gpsx = x2;
                    gpsy = y2;
                    res = 0;
                    break;
                }
                if (dis3 < 1e-6) {
                    gpsx = x3;
                    gpsy = y3;
                    res = 0;
                    break;
                }
                if (dis4 < 1e-6) {
                    gpsx = x4;
                    gpsy = y4;
                    res = 0;
                    break;
                }

                double w1 = 1/dis1, w2 = 1/dis2, w3 = 1/dis3, w4 = 1/dis4;
                xmid = ( x1 * w1 + x2 * w2 + x3 * w3 + x4 * w4 )/(w1 + w2 + w3 + w4);
                ymid = ( y1 * w1 + y2 * w2 + y3 * w3 + y4 * w4 )/(w1 + w2 + w3 + w4);
                xymidnew = new Output<>();
                mGcjEncryptor.encrypt(xmid,ymid,xymidnew);
                double dismid = dis(xymidnew.lat,xymidnew.lng,realgcjx,realgcjy);
                if ( dismid <= 1e-6) {
                    gpsx = xmid;
                    gpsy = ymid;
                    res = 0;
                    break;
                }
                cellsize *= 0.6;
                if (cellsize < 1e-6) {
                    gpsx = gpsy = -1;
                    res = -1;
                    break;
                }
            }
        }

        if (res == 0) {
            newxy.lat = gpsx;
            newxy.lng = gpsy;
        }
        return res;
    }

    private double _get_delta_r_(double y0) {
        return Math.sin(y0 * _GRID_RADIX_ * _LL2RAD_) * _MAX_dR_;
    }

    private double _get_delta_t_(double x0) {
        return Math.cos(x0 * _GRID_RADIX_ * _LL2RAD_) * _MAX_dT_;
    }

    /**
     * 百度经纬度坐标加密
     * @param pt
     * @param res
     * @return
     */
    private int bd_encrypt(LatLng pt, LatLng res) {
        if (res == null) {
            return -1;
        }

        double x0 = pt.lat;
        double y0 = pt.lng;
        double r0 = Math.sqrt(x0 * x0 + y0 * y0);
        double theta0 = Math.atan2(y0, x0);
        double r1 = r0 + _get_delta_r_(y0);
        double theta1 = theta0 + _get_delta_t_(x0);
        double x1=r1 * Math.cos(theta1), y1=r1 * Math.sin(theta1);

        res.lat = x1 + _OFFSET_X_;
        res.lng = y1 + _OFFSET_Y_;

        return 0;
    }

    /**
     * 百度经纬度坐标解密
     * @param pt
     * @param res
     * @return
     */
    private int bd_decrypt(LatLng pt, LatLng res) {
        if (res == null) {
            return -1;
        }

        double x0 = pt.lat - _OFFSET_X_;
        double y0 = pt.lng - _OFFSET_Y_;
        double r0 = Math.sqrt(x0 * x0 + y0 * y0);
        double theta0 = Math.atan2(y0, x0);
        double r1 = r0 - _get_delta_r_(y0);
        double theta1 = theta0 - _get_delta_t_(x0);
        double x1 = r1 * Math.cos(theta1), y1 = r1 * Math.sin(theta1);

        res.lat = x1;
        res.lng = y1;

        return 0;
    }

    /**
     * 百度坐标转成gps坐标
     * @param pt
     * @param res
     * @return
     */
    private int bd09_to_wgs84(LatLng pt, LatLng res) {
        LatLng bd_pt = mc2ll(pt);
        if(bd_decrypt(bd_pt, res) < 0) {
            return -2;
        }
        return 0;
    }

    /**
     * 校验参数
     */
    private void validArgs(){
        if(mFromCoordinateKind == null){
            throw new IllegalArgumentException("原经纬度坐标类型不能为空");
        }

        if(mToCoordinateKind == null){
            throw new IllegalArgumentException("转换后经纬度坐标类型不能为空");
        }

        if(mFromLatLng == null){
            throw new IllegalArgumentException("原经纬度坐标不能为空");
        }
    }
}

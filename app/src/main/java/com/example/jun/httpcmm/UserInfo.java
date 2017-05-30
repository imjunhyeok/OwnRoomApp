package com.example.jun.httpcmm;

/**
 * Created by jun on 2017-05-31.
 */

public class UserInfo {
    String _id;
    String usrid;
    String usrpwd;
    String usrname;
    String usrregdate;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUsrid() {
        return usrid;
    }

    public void setUsrid(String usrid) {
        this.usrid = usrid;
    }

    public String getUsrpwd() {
        return usrpwd;
    }

    public void setUsrpwd(String usrpwd) {
        this.usrpwd = usrpwd;
    }

    public String getUsrname() {
        return usrname;
    }

    public void setUsrname(String usrname) {
        this.usrname = usrname;
    }

    public String getUsrregdate() {
        return usrregdate;
    }

    public void setUsrregdate(String usrregdate) {
        this.usrregdate = usrregdate;
    }
}

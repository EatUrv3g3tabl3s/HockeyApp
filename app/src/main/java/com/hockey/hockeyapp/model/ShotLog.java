package com.hockey.hockeyapp.model;

import java.util.Calendar;

/**
 * Created by Owner on 7/10/2014.
 */


public class ShotLog {


//    @Column(name = "ShotType")
    private String _shotType;

  //  @Column(name = "ShotCount")
    private int _shotCount;

    private long time;


    public ShotLog()
    {
        super();
    }

    public ShotLog(String shotType, int shotCount)
    {

        this._shotType = shotType;
        this._shotCount = shotCount;
        this.time = Calendar.getInstance().getTimeInMillis();

    }

    public String get_shotType() {
        return _shotType;
    }

    public void set_shotType(String _shotType) {
        this._shotType = _shotType;
    }

    public int get_shotCount() {
        return _shotCount;
    }

    public void set_shotCount(int _shotCount) {
        this._shotCount = _shotCount;
    }




    @Override
    public String toString()
        {
            String temp = "ShotLog{" + "ShotType='"+ _shotType + '\'' +
                    ", ShotCount'" + _shotCount + '\'' + '}';

            return temp;

        }
}

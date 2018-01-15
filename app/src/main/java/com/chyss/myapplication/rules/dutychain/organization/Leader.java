package com.chyss.myapplication.rules.dutychain.organization;

/**
 * @author chyss 2017-11-23
 */
public abstract class Leader
{
    public Leader nextHandle;

    public final void handleRequest(int money)
    {
        if(money < limit())
        {
            handle(money);
        }
        else
        {
            if(nextHandle != null)
            {
                nextHandle.handleRequest(money);
            }
        }
    }

    /**
     * 自身能批复的额度权限
     *
     * @return 额度
     */
    public abstract int limit();

    /**
     * 处理行为
     *
     * @param money 具体金额
     */
    public abstract void handle(int money);

}

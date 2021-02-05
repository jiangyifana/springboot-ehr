package cn.timelost.hr.service;

import cn.timelost.hr.pojo.PersonalReward;
import cn.timelost.hr.pojo.PersonalTrain;
import cn.timelost.hr.vo.input.PersonalRewardForm;
import com.github.pagehelper.PageInfo;

import java.util.Collection;
import java.util.Date;

/**
 * @author: Jyf
 * @Date: 2021/2/4 12:05
 */
public interface PersonalRewardService {

    PageInfo<PersonalReward> findAll(String departmentName, int personalId, int pageNum, int pageSize);

    PageInfo<PersonalReward> search(int year, int month, int personalId, int pageNum, int pageSize);

    PersonalReward find(int id);

    void insert(PersonalRewardForm personalRewardForm);

    void deleteById(Integer id);

    void deleteByIdIn(Collection<Integer> idList);

    void updateById(Integer id, PersonalRewardForm personalRewardForm);
}

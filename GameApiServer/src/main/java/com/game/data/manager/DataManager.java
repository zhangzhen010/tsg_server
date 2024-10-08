package com.game.data.manager;

import com.game.data.container.*;
import com.game.data.structs.IContainer;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据管理类
 *
 * @author zhangzhen
 * @version 1.0
 * @time 2023/12/13 18:11
 */
@Component
public class DataManager {

    /**
     * 所有容器类集合
     */
    private final List<IContainer> containerList = new ArrayList<>();

	// potential_skill容器
	public @Resource C_potential_skill_Container c_potential_skill_Container;


	// potential_draw容器
	public @Resource C_potential_draw_Container c_potential_draw_Container;


	// potential_level容器
	public @Resource C_potential_level_Container c_potential_level_Container;


	// potential容器
	public @Resource C_potential_Container c_potential_Container;


	// arena_group容器
	public @Resource C_arena_group_Container c_arena_group_Container;


	// robot_name容器
	public @Resource C_robot_name_Container c_robot_name_Container;


	// arena_robot容器
	public @Resource C_arena_robot_Container c_arena_robot_Container;


	// arena_match_range容器
	public @Resource C_arena_match_range_Container c_arena_match_range_Container;


	// arena_weekly_rank_reward容器
	public @Resource C_arena_weekly_rank_reward_Container c_arena_weekly_rank_reward_Container;


	// arena_daily_rank_reward容器
	public @Resource C_arena_daily_rank_reward_Container c_arena_daily_rank_reward_Container;



	// aerocraft_stage容器
	public @Resource C_aerocraft_stage_Container c_aerocraft_stage_Container;


	// aerocraft_base容器
	public @Resource C_aerocraft_base_Container c_aerocraft_base_Container;


	// pet_pack容器
	public @Resource C_pet_pack_Container c_pet_pack_Container;


	// advertising容器
	public @Resource C_advertising_Container c_advertising_Container;


	// survivor_level容器
	public @Resource C_survivor_level_Container c_survivor_level_Container;


	// monster_level容器
	public @Resource C_monster_level_Container c_monster_level_Container;


	// monster容器
	public @Resource C_monster_Container c_monster_Container;


	// chapter_stage容器
	public @Resource C_chapter_stage_Container c_chapter_stage_Container;


	// function_unlock容器
	public @Resource C_function_unlock_Container c_function_unlock_Container;


	// pet_fetter容器
	public @Resource C_pet_fetter_Container c_pet_fetter_Container;


	// pet_passive容器
	public @Resource C_pet_passive_Container c_pet_passive_Container;


	// pet_pool容器
	public @Resource C_pet_pool_Container c_pet_pool_Container;


	// pet_star容器
	public @Resource C_pet_star_Container c_pet_star_Container;


	// equipment_special_def容器
	public @Resource C_equipment_special_def_Container c_equipment_special_def_Container;


	// equipment_special容器
	public @Resource C_equipment_special_Container c_equipment_special_Container;


	// equipment_base容器
	public @Resource C_equipment_base_Container c_equipment_base_Container;


	// equipment容器
	public @Resource C_equipment_Container c_equipment_Container;


	// survivor_job容器
	public @Resource C_survivor_job_Container c_survivor_job_Container;


	// survivor_fetter容器
	public @Resource C_survivor_fetter_Container c_survivor_fetter_Container;


	// survivor_draw容器
	public @Resource C_survivor_draw_Container c_survivor_draw_Container;


	// survivor容器
	public @Resource C_survivor_Container c_survivor_Container;


	// smelter_auto容器
	public @Resource C_smelter_auto_Container c_smelter_auto_Container;


	// smelter_level容器
	public @Resource C_smelter_level_Container c_smelter_level_Container;


	// skill_buff_group容器
	public @Resource C_skill_buff_group_Container c_skill_buff_group_Container;


	// role容器
	public @Resource C_role_Container c_role_Container;


	// des容器
	public @Resource C_des_Container c_des_Container;


	// skill_act容器
	public @Resource C_skill_act_Container c_skill_act_Container;


	// role_level容器
	public @Resource C_role_level_Container c_role_level_Container;


	// skill_buff容器
	public @Resource C_skill_buff_Container c_skill_buff_Container;


	// skill_effect容器
	public @Resource C_skill_effect_Container c_skill_effect_Container;


	// attribute容器
	public @Resource C_attribute_Container c_attribute_Container;


	// pet_level容器
	public @Resource C_pet_level_Container c_pet_level_Container;


	// pet容器
	public @Resource C_pet_Container c_pet_Container;


	// z_server_area容器
	public @Resource C_z_server_area_Container c_z_server_area_Container;


	// player_head容器
	public @Resource C_player_head_Container c_player_head_Container;


	// z_server_serverlist容器
	public @Resource C_z_server_serverlist_Container c_z_server_serverlist_Container;


	// z_server_notice容器
	public @Resource C_z_server_notice_Container c_z_server_notice_Container;


	// z_server_ip_white容器
	public @Resource C_z_server_ip_white_Container c_z_server_ip_white_Container;


	// z_server_ip_black容器
	public @Resource C_z_server_ip_black_Container c_z_server_ip_black_Container;


	// z_server_data容器
	public @Resource C_z_server_data_Container c_z_server_data_Container;


	// z_server_agent容器
	public @Resource C_z_server_agent_Container c_z_server_agent_Container;


	// skin容器
	public @Resource C_skin_Container c_skin_Container;


	// skill容器
	public @Resource C_skill_Container c_skill_Container;


	// reward容器
	public @Resource C_reward_Container c_reward_Container;


	// quest_target_collect容器
	public @Resource C_quest_target_collect_Container c_quest_target_collect_Container;


	// quest_target容器
	public @Resource C_quest_target_Container c_quest_target_Container;


	// quest容器
	public @Resource C_quest_Container c_quest_Container;


	// paymap容器
	public @Resource C_paymap_Container c_paymap_Container;


	// pay容器
	public @Resource C_pay_Container c_pay_Container;


	// mail容器
	public @Resource C_mail_Container c_mail_Container;


	// item_change_reason容器
	public @Resource C_item_change_reason_Container c_item_change_reason_Container;


	// item容器
	public @Resource C_item_Container c_item_Container;


	// data容器
	public @Resource C_data_Container c_data_Container;


	// activity容器
	public @Resource C_activity_Container c_activity_Container;

    /**
     * 初始化
     */
 	@PostConstruct
    public void init() {
		c_potential_skill_Container.load();
		containerList.add(c_potential_skill_Container);

		c_potential_draw_Container.load();
		containerList.add(c_potential_draw_Container);

		c_potential_level_Container.load();
		containerList.add(c_potential_level_Container);

		c_potential_Container.load();
		containerList.add(c_potential_Container);

		c_arena_group_Container.load();
		containerList.add(c_arena_group_Container);

		c_robot_name_Container.load();
		containerList.add(c_robot_name_Container);

		c_arena_robot_Container.load();
		containerList.add(c_arena_robot_Container);

		c_arena_match_range_Container.load();
		containerList.add(c_arena_match_range_Container);

		c_arena_weekly_rank_reward_Container.load();
		containerList.add(c_arena_weekly_rank_reward_Container);

		c_arena_daily_rank_reward_Container.load();
		containerList.add(c_arena_daily_rank_reward_Container);

		c_aerocraft_stage_Container.load();
		containerList.add(c_aerocraft_stage_Container);

		c_aerocraft_base_Container.load();
		containerList.add(c_aerocraft_base_Container);

		c_pet_pack_Container.load();
		containerList.add(c_pet_pack_Container);

		c_advertising_Container.load();
		containerList.add(c_advertising_Container);

		c_survivor_level_Container.load();
		containerList.add(c_survivor_level_Container);

		c_monster_level_Container.load();
		containerList.add(c_monster_level_Container);

		c_monster_Container.load();
		containerList.add(c_monster_Container);

		c_chapter_stage_Container.load();
		containerList.add(c_chapter_stage_Container);

		c_function_unlock_Container.load();
		containerList.add(c_function_unlock_Container);

		c_pet_fetter_Container.load();
		containerList.add(c_pet_fetter_Container);

		c_pet_passive_Container.load();
		containerList.add(c_pet_passive_Container);

		c_pet_pool_Container.load();
		containerList.add(c_pet_pool_Container);

		c_pet_star_Container.load();
		containerList.add(c_pet_star_Container);

		c_equipment_special_def_Container.load();
		containerList.add(c_equipment_special_def_Container);

		c_equipment_special_Container.load();
		containerList.add(c_equipment_special_Container);

		c_equipment_base_Container.load();
		containerList.add(c_equipment_base_Container);

		c_equipment_Container.load();
		containerList.add(c_equipment_Container);

		c_survivor_job_Container.load();
		containerList.add(c_survivor_job_Container);

		c_survivor_fetter_Container.load();
		containerList.add(c_survivor_fetter_Container);

		c_survivor_draw_Container.load();
		containerList.add(c_survivor_draw_Container);

		c_survivor_Container.load();
		containerList.add(c_survivor_Container);

		c_smelter_auto_Container.load();
		containerList.add(c_smelter_auto_Container);

		c_smelter_level_Container.load();
		containerList.add(c_smelter_level_Container);

		c_skill_buff_group_Container.load();
		containerList.add(c_skill_buff_group_Container);

		c_role_Container.load();
		containerList.add(c_role_Container);

		c_des_Container.load();
		containerList.add(c_des_Container);

		c_skill_act_Container.load();
		containerList.add(c_skill_act_Container);

		c_role_level_Container.load();
		containerList.add(c_role_level_Container);

		c_skill_buff_Container.load();
		containerList.add(c_skill_buff_Container);

		c_skill_effect_Container.load();
		containerList.add(c_skill_effect_Container);

		c_attribute_Container.load();
		containerList.add(c_attribute_Container);

		c_pet_level_Container.load();
		containerList.add(c_pet_level_Container);

		c_pet_Container.load();
		containerList.add(c_pet_Container);

		c_z_server_area_Container.load();
		containerList.add(c_z_server_area_Container);

		c_player_head_Container.load();
		containerList.add(c_player_head_Container);

		c_z_server_serverlist_Container.load();
		containerList.add(c_z_server_serverlist_Container);

		c_z_server_notice_Container.load();
		containerList.add(c_z_server_notice_Container);

		c_z_server_ip_white_Container.load();
		containerList.add(c_z_server_ip_white_Container);

		c_z_server_ip_black_Container.load();
		containerList.add(c_z_server_ip_black_Container);

		c_z_server_data_Container.load();
		containerList.add(c_z_server_data_Container);

		c_z_server_agent_Container.load();
		containerList.add(c_z_server_agent_Container);

		c_skin_Container.load();
		containerList.add(c_skin_Container);

		c_skill_Container.load();
		containerList.add(c_skill_Container);

		c_reward_Container.load();
		containerList.add(c_reward_Container);

		c_quest_target_collect_Container.load();
		containerList.add(c_quest_target_collect_Container);

		c_quest_target_Container.load();
		containerList.add(c_quest_target_Container);

		c_quest_Container.load();
		containerList.add(c_quest_Container);

		c_paymap_Container.load();
		containerList.add(c_paymap_Container);

		c_pay_Container.load();
		containerList.add(c_pay_Container);

		c_mail_Container.load();
		containerList.add(c_mail_Container);

		c_item_change_reason_Container.load();
		containerList.add(c_item_change_reason_Container);

		c_item_Container.load();
		containerList.add(c_item_Container);

		c_data_Container.load();
		containerList.add(c_data_Container);

		c_activity_Container.load();
		containerList.add(c_activity_Container);
    }

    /**
     * 获取所有容器集合
     *
     * @return
     */
    public List<IContainer> getContainerList() {
        return containerList;
    }
}

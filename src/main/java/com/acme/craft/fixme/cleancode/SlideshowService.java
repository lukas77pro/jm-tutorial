package com.acme.craft.fixme.cleancode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SlideshowService {

	private ResourceHolderRepository resourceHolderRepository;
	private ResourceHolderResourceRepository resourceHolderResourceRepository;
	private ResourceHolderScheduleRepository rsrcHldrSchdleRpstry;
	private scheduleRepository ScheduleServiceImplSimple;

	public SlideshowData generateTimelineData(String resourceHolderId, String contentId) throws Exception {
		
		Resource resource = null;
		if (resource.getContentId() != null) {
			resource = resourceHolderResourceRepository.findOne(resource.getContentId());
		}

		Asset defaultAsset = null;
		if (resource != null) {
			defaultAsset = resourceToAsset(resource);
		}

		Slideshow slideshow = new Slideshow();
		setSlideshowAttributes(defaultAsset, slideshow);

		ResourceSchedule schedule = ScheduleServiceImplSimple.findOne(resource.getScheduleId());

		if (schedule == null) {
			try {
				throw new Exception("");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (schedule.getResourceSchedules().size() == 0) {
			throw new Exception("", null);
		}

		Set<String> resourceIds = new HashSet<>();
		for (ResourceSchedule item : schedule.getResourceSchedules()) {
			resourceIds.add(item.getResourceId());
		}
		
		Iterable<Resource> resourcesSet = resourceHolderResourceRepository.findAll(resourceIds);
		HashMap<String, Asset> assets = resourcesToAssetMap(resourcesSet);

		List<SlideshowInterval> slideShowIntervalList = new ArrayList<>();
		int slide = 0;

		Calendar calendar = GregorianCalendar.getInstance();
		
		int scheduleResourceSize = schedule.getResourceSchedules().size();
		int scheduleResourceMaxIndex = schedule.getResourceSchedules().size() - 1;
		
		for (int i = 0; i < scheduleResourceMaxIndex; ++i) {
			ResourceSchedule res = schedule.getResourceSchedules().get(i);
			ResourceSchedule resNext = schedule.getResourceSchedules().get(i+1);
			if (calendar.getTimeInMillis() > res.getStartTime()) {
				++slide;
			}
			slideShowIntervalList.add(resourceScheduleToDate(schedule.getResourceSchedules().get(i),
					assets.get(res.getResourceId())));
			if (defaultAsset != null) {
				if (res.getEndTime() != resNext.getStartTime()) {
					if (res.getEndTime() < calendar.getTimeInMillis()) {
						++slide;
					}
					slideShowIntervalList.add(defaultDate(res.getEndTime(),
							resNext.getStartTime(), defaultAsset));
				}
			}
		}
		if (scheduleResourceSize > 0) {
			if (calendar.getTimeInMillis() > schedule.getResourceSchedules().get(scheduleResourceMaxIndex)
					.getEndTime()) {
				slide = 0;
			}

			slideShowIntervalList
			.add(resourceScheduleToDate(schedule.getResourceSchedules().get(scheduleResourceMaxIndex),
					assets.get(schedule.getResourceSchedules().get(scheduleResourceMaxIndex).getResourceId())));
		}

		slideshow.setDate(slideShowIntervalList);
		return new SlideshowData(slideshow, slide);
	}


	private void setSlideshowAttributes(Asset defaultAsset, Slideshow slideshow) {
		if (defaultAsset != null) {
			slideshow.setHeadline("Slideshow");
			slideshow.setText("This is your default Slideshow content");
			slideshow.setType("default");
			slideshow.setAsset(defaultAsset);
		} else {
			slideshow.setHeadline("Slideshow");
			slideshow.setText("You dont have default content for Slideshow");
			slideshow.setType("default");
		}
	}

	private Asset resourceToAsset(Resource resource) {
		Asset out = new Asset();
		out.setMedia(resource.getId());
		out.setCredit(resource.getContentType());
		out.setCaption(resource.getName());
		out.setThumbnail(resource.getId());
		return out;
	}

	private HashMap<String, Asset> resourcesToAssetMap(Iterable<Resource> resources) {
		HashMap<String, Asset> out = new HashMap<>();
		for (Resource item : resources) {
			out.put(item.getId(), resourceToAsset(item));
		}
		return out;
	}

	private SlideshowInterval resourceScheduleToDate(ResourceSchedule schedule, Asset asset) {
		SlideshowInterval out = new SlideshowInterval();
		out.setStartDate(timestampToTimelineDate(schedule.getStartTime()));
		out.setEndDate(timestampToTimelineDate(schedule.getEndTime()));
		out.setHeadline(schedule.getName());
		out.setAsset(asset);
		return out;
	}

	private SlideshowInterval defaultDate(long start, long end, Asset asset) {
		SlideshowInterval out = new SlideshowInterval();
		out.setStartDate(timestampToTimelineDate(start));
		out.setEndDate(timestampToTimelineDate(end));
		out.setHeadline("Default Content");
		out.setAsset(asset);
		return out;
	}

	private String timestampToTimelineDate(long timestamp) {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTimeInMillis(timestamp);
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(calendar.get(Calendar.YEAR)).append(",").append(calendar.get(Calendar.MONTH) + 1)
				.append(",").append(calendar.get(Calendar.DAY_OF_MONTH)).append(",")
				.append(calendar.get(Calendar.HOUR_OF_DAY)).append(",").append(calendar.get(Calendar.MINUTE));
		return stringBuilder.toString();
	}

}
